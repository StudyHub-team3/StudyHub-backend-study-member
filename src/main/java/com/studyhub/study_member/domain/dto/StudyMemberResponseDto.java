package com.studyhub.study_member.domain.dto;

import com.studyhub.study_member.domain.entity.StudyMember;
import lombok.Getter;
import lombok.Setter;

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

    public static StudyMemberResponseDto fromEntity(StudyMember member) {
        StudyMemberResponseDto dto = new StudyMemberResponseDto();

        dto.id = member.getId();
        dto.studyId = member.getStudyId();
        dto.userId = member.getUserId();
        dto.status = member.getStatus();
        dto.role = member.getRole();
        dto.comment = member.getComment();

        return dto;
    }
}
