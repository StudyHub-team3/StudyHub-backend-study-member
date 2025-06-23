package com.studyhub.study_member.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "study_member",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"studyId", "userId"})
        }
)
public class StudyMember {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Column(nullable = false)
    @Getter
    @Setter
    private Long studyId;

    @Column(nullable = false)
    @Getter
    @Setter
    private Long userId;

    @Column
    @Getter
    @Setter
    private String status;

    @Column
    @Getter
    @Setter
    private String role;

    @Column
    @Getter
    @Setter
    private String comment;

    @Column(nullable = false)
    @Getter
    @Setter
    private boolean isLeader = false;

    @Column
    @Getter
    @Setter
    private LocalDateTime joinAt;

    @Column
    @Getter
    @Setter
    private LocalDateTime requestAt = LocalDateTime.now();
}
