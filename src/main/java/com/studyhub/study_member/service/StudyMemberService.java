package com.studyhub.study_member.service;

import com.studyhub.study_member.common.exception.NotFound;
import com.studyhub.study_member.domain.dto.StudyMemberRequestJoinDto;
import com.studyhub.study_member.domain.dto.StudyMemberResponseDto;
import com.studyhub.study_member.domain.entity.Status;
import com.studyhub.study_member.domain.entity.StudyMember;
import com.studyhub.study_member.domain.event.KafkaEvent;
import com.studyhub.study_member.domain.event.dto.StudyMemberEventDto;
import com.studyhub.study_member.domain.repository.StudyMemberRepository;
import com.studyhub.study_member.event.producer.KafkaMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyMemberService {
    private final StudyMemberRepository studyMemberRepository;
    private final KafkaMessageProducer kafkaMessageProducer;

    @Transactional(readOnly = true)
    public List<StudyMemberResponseDto> getAcceptedMembers(Long studyId) {
        List<StudyMember> member = studyMemberRepository.findByStudyIdAndStatus(studyId, Status.valueOf("A"));

        return member.stream()
                .map(StudyMemberResponseDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<StudyMemberResponseDto> getPendingRequests(Long studyId) {
        List<StudyMember> member = studyMemberRepository.findByStudyIdAndStatus(studyId, Status.valueOf("P"));

        return member.stream()
                .map(StudyMemberResponseDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public StudyMemberResponseDto getUserData(Long studyId, Long userId) {
        StudyMember member = studyMemberRepository.findByStudyIdAndUserId(studyId, userId)
                .orElseThrow(() -> new NotFound("참여자가 아닙니다."));

        return StudyMemberResponseDto.fromEntity(member);
    }

    @Transactional
    public void requestJoin(StudyMemberRequestJoinDto requestJoinDto, Long userId, String userName) {
        if (studyMemberRepository.existsByStudyIdAndUserId(requestJoinDto.getStudyId(), userId)) {
            throw new IllegalStateException("이미 신청하거나 참여 중인 사용자입니다.");
        }

        StudyMember member = requestJoinDto.toEntity();
        member.setUserId(userId);
        member.setUserName(userName);

        studyMemberRepository.save(member);
    }

    @Transactional
    public void acceptJoinRequest(Long memberId) {
        StudyMember member = studyMemberRepository.findById(memberId)
                .orElseThrow(() -> new NotFound("참여 신청 내역이 없습니다."));

        if (!"P".equals(member.getStatus().name())) {
            throw new IllegalStateException("대기 중인 신청이 아닙니다.");
        }

        member.setStatus(Status.A);
        member.setJoinAt(LocalDateTime.now());

        studyMemberRepository.save(member);

        KafkaEvent<StudyMemberEventDto> event = new KafkaEvent<>("STUDY_CREW_JOINED", StudyMemberEventDto.fromEntity(member));
        kafkaMessageProducer.send("study-member", event);
    }

    @Transactional
    public void rejectJoinRequest(Long memberId) {
        StudyMember member = studyMemberRepository.findById(memberId)
                .orElseThrow(() -> new NotFound("참여 신청 내역이 없습니다."));

        if (!"P".equals(member.getStatus().name())) {
            throw new IllegalStateException("대기 중인 신청이 아닙니다.");
        }

        studyMemberRepository.delete(member);
    }

    @Transactional
    public void leaveStudy(Long studyId, Long userId) {
        StudyMember member = studyMemberRepository.findByStudyIdAndUserId(studyId, userId)
                .orElseThrow(() -> new NotFound("참여자가 아닙니다."));

        if (member.isLeader()) {
            throw new IllegalStateException("스터디 리더는 탈퇴할 수 없습니다.");
        }

        studyMemberRepository.delete(member);

        KafkaEvent<StudyMemberEventDto> event = new KafkaEvent<>("STUDY_CREW_QUITED", StudyMemberEventDto.fromEntity(member));
        kafkaMessageProducer.send("study-member", event);
    }

    @Transactional
    public void createLeader(StudyMember member) {
        studyMemberRepository.save(member);
    }

    @Transactional
    public void handleStudyDeletedEvent(Long studyId) {
        studyMemberRepository.deleteByStudyId(studyId);
    }
}
