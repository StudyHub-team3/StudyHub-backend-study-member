package com.studyhub.study_member.domain.repository;

import com.studyhub.study_member.domain.entity.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {
    List<StudyMember> findByStudyId(Long studyId);

    Optional<StudyMember> findByStudyIdAndUserId(Long studyId, String userId);

    List<StudyMember> findByStudyIdAndStatus(Long studyId, String status);

    boolean existsByStudyIdAndUserId(Long studyId, String userId);
}
