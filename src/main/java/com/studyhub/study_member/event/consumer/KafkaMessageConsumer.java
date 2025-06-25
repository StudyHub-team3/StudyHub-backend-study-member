package com.studyhub.study_member.event.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
            topics = StudyEvent.Topic
            )
    void handleStudyEvent(String message, Acknowledgment ack) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(message);
            String eventType = root.get("eventType").asText();

            switch (eventType) {
                case "STUDY_CREATED" -> {
                    StudyEvent.studyCreatedData data =
                            mapper.treeToValue(root.get("data"), StudyEvent.studyCreatedData.class);
                    StudyMember member = data.toEntity();
                    studyMemberService.createLeader(member);
                }

                case "STUDY_DELETED" -> {
                    StudyEvent.studyDeletedData data =
                            mapper.treeToValue(root.get("data"), StudyEvent.studyDeletedData.class);
                    studyMemberService.handleStudyDeletedEvent(data.getStudyId());
                }

                default -> log.warn("Unknown event type: {}", eventType);
            }

            ack.acknowledge();
        } catch (Exception e) {
            log.error("Kafka 메시지 처리 중 예외 발생", e);
        }
    }
}
