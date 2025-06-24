package com.studyhub.study_member.domain.dto;

import com.studyhub.study_member.domain.entity.Role;
import com.studyhub.study_member.domain.entity.Status;
import com.studyhub.study_member.domain.entity.StudyMember;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StudyMemberRequestJoinDto {
    private Long studyId;

    private String role;

    private String comment;

    public StudyMember toEntity() {
        StudyMember member = new StudyMember();

        member.setStudyId(this.studyId);
        member.setStatus(Status.P);
        member.setRole(Role.valueOf(this.role));
        member.setLeader(false);
        member.setJoinAt(null);
        member.setComment(this.comment);

        return member;
    }
}
