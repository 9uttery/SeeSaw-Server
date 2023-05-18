package com._8attery.seesaw.domain.project_record;

import com._8attery.seesaw.domain.project.Project;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SS_PROJECT_RECORD")
@Entity
public class ProjectRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_record_id")
    private Long id;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "question")
    private String question; // 기록 질문

    @Column(name = "contents", nullable = false)
    private String contents; // 기록 내용

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // 생성 날짜

    @Column(name = "temp", nullable = false)
    private Boolean temp; // 임시 저장 여부

//    @Builder
//    public ProjectRecord(String contents, LocalDateTime createdAt, Boolean temp) {
//        this.contents = contents;
//        this.createdAt = createdAt;
//        this.temp = temp;
//    }

    @Builder(builderMethodName = "projectRecordBuilder")
    public ProjectRecord(Project project, String question, String contents, LocalDateTime createdAt, Boolean temp) {
        this.project = project;
        this.question = question;
        this.contents = contents;
        this.createdAt = createdAt;
        this.temp = temp;
    }
}
