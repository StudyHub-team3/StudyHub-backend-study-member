package com.studyhub.study_member.event.consumer.study;

import com.studyhub.study_member.domain.entity.Role;
import com.studyhub.study_member.domain.entity.Status;
import com.studyhub.study_member.domain.entity.StudyMember;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StudyCreatedEvent {
    public static final String Topic = "study";

    private Long studyId;
    private Long userId;
    private String userName;
    private String role;

    public StudyMember toEntity() {
        StudyMember member = new StudyMember();

        member.setStudyId(this.studyId);
        member.setStatus(Status.A);
        member.setRole(Role.valueOf(this.role));
        member.setLeader(true);
        member.setJoinAt(LocalDateTime.now());
        member.setComment(null);
        member.setUserId(this.userId);
        member.setUserName(this.userName);

        return member;
    }
}
