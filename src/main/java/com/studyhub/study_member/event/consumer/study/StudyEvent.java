package com.studyhub.study_member.event.consumer.study;

import com.studyhub.study_member.domain.entity.Role;
import com.studyhub.study_member.domain.entity.Status;
import com.studyhub.study_member.domain.entity.StudyMember;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StudyEvent<T> {
    public static final String Topic = "study";

    private String eventType;
    private T data;
    private String timestamp;

    @Getter
    @Setter
    public static class studyCreatedData {
        private Long studyId;
        private Long userId;
        private String userName;
        private String creatorRole;

        public StudyMember toEntity() {
            StudyMember member = new StudyMember();

            member.setStudyId(this.studyId);
            member.setStatus(Status.A);
            member.setRole(Role.valueOf(this.creatorRole));
            member.setLeader(true);
            member.setJoinAt(LocalDateTime.now());
            member.setComment(null);
            member.setUserId(this.userId);
            member.setUserName(this.userName);

            return member;
        }
    }

    @Getter
    @Setter
    public static class studyDeletedData {
        private Long studyId;
        private Long userId;
    }
}
