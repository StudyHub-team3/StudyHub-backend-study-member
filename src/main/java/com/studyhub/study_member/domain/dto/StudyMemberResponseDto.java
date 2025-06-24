package com.studyhub.study_member.domain.dto;

import com.studyhub.study_member.domain.entity.StudyMember;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class StudyMemberResponseDto {
    private Long id;
    private Long studyId;
    private Long userId;
    private String userName;
    private String status;
    private String role;
    private String comment;
    private String requestAt;

    public static StudyMemberResponseDto fromEntity(StudyMember member) {
        StudyMemberResponseDto dto = new StudyMemberResponseDto();

        dto.id = member.getId();
        dto.studyId = member.getStudyId();
        dto.userId = member.getUserId();
        dto.userName = member.getUserName();
        dto.status = member.getStatus().name();
        dto.role = member.getRole().name();
        dto.comment = member.getComment();
        dto.requestAt = member.getRequestAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return dto;
    }
}
