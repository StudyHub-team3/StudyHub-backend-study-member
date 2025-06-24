package com.studyhub.study_member.event.consumer;

import com.studyhub.study_member.domain.entity.StudyMember;
import com.studyhub.study_member.event.consumer.study.StudyCreatedEvent;
import com.studyhub.study_member.event.consumer.study.StudyDeletedEvent;
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
public class KafkaMessageConsumer {
    private final StudyMemberService studyMemberService;

    @KafkaListener(
            topics = StudyCreatedEvent.Topic,
            properties = {
                    JsonDeserializer.VALUE_DEFAULT_TYPE
                        + ":com.studyhub.study_member.event.consumer.study.StudyCreatedEvent"
            })
    void handleStudyCreatedEvent(StudyCreatedEvent event, Acknowledgment ack) {
        StudyMember member = event.toEntity();

        studyMemberService.createLeader(member);

        ack.acknowledge();
    }

    @KafkaListener(
            topics = StudyDeletedEvent.Topic,
            properties = {
                    JsonDeserializer.VALUE_DEFAULT_TYPE
                        + ":com.studyhub.study_member.event.consumer.study.StudyDeletedEvent"
            })
    void handleStudyDeletedEvent(StudyDeletedEvent event, Acknowledgment ack) {
        studyMemberService.handleStudyDeletedEvent(event.getStudyId());

        ack.acknowledge();
    }
}
