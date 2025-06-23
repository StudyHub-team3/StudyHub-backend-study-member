package com.studyhub.study_member.domain.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KafkaEvent<T> {
    private String eventType;
    private T data;
    private String timestamp;

    public KafkaEvent(String eventType, T data) {
        this.eventType = eventType;
        this.data = data;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
