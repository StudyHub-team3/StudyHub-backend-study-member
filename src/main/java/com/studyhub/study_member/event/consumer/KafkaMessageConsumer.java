package com.studyhub.study_member.event.consumer;

import com.studyhub.study_member.domain.entity.StudyMember;
import com.studyhub.study_member.event.consumer.study.StudyEvent;
import com.studyhub.study_member.service.StudyMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaMessageConsumer<T> {
    private final StudyMemberService studyMemberService;

    @KafkaListener(
            topics = StudyEvent.Topic,
            properties = {
                    JsonDeserializer.VALUE_DEFAULT_TYPE
                        + ":com.studyhub.study_member.event.consumer.study.StudyEvent"
            })
    void handleStudyEvent(StudyEvent<T> event, Acknowledgment ack) {
        if(event.getEventType().equals("STUDY_CREATED")) {
            StudyEvent.studyCreatedData data = (StudyEvent.studyCreatedData) event.getData();

            StudyMember member = data.toEntity();

            studyMemberService.createLeader(member);
        } else {
            StudyEvent.studyDeletedData data = (StudyEvent.studyDeletedData) event.getData();

            studyMemberService.handleStudyDeletedEvent(data.getStudyId());
        }

        ack.acknowledge();
    }
}
