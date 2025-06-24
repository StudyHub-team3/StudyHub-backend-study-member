package com.studyhub.study_member.domain.repository;

import com.studyhub.study_member.domain.entity.Status;
import com.studyhub.study_member.domain.entity.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {
    Optional<StudyMember> findByStudyIdAndUserId(Long studyId, Long userId);

    List<StudyMember> findByStudyIdAndStatus(Long studyId, Status status);

    boolean existsByStudyIdAndUserId(Long studyId, Long userId);

    void deleteByStudyId(Long studyId);
}
