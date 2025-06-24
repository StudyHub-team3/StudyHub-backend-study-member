package com.studyhub.study_member.domain.event.dto;

import com.studyhub.study_member.domain.entity.StudyMember;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudyMemberEventDto {
    private Long studyId;
    private Long userId;
    private String role;

    public static StudyMemberEventDto fromEntity(StudyMember member) {
        StudyMemberEventDto eventDto = new StudyMemberEventDto();

        eventDto.studyId = member.getStudyId();
        eventDto.userId = member.getUserId();
        eventDto.role = member.getRole();

        return eventDto;
    }
}
