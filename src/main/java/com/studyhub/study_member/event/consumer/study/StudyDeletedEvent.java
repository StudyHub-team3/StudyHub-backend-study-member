package com.studyhub.study_member.event.consumer.study;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudyDeletedEvent {
    public static final String Topic = "study";

    private Long studyId;
    private Long userId;
}
