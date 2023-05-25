package com._8attery.seesaw.domain.project_record;

import com._8attery.seesaw.domain.project.Project;
import com._8attery.seesaw.domain.project_question.ProjectQuestion;
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
    @Column(name = "project_record_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "project_question_id")
    private ProjectQuestion projectQuestion; // 기록 질문

    @Column(name = "contents", nullable = false)
    private String contents; // 기록 내용

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // 생성 날짜

    @Column(name = "temp", nullable = false)
    private Boolean temp; // 임시 저장 여부

    @Builder
    public ProjectRecord(Project project, ProjectQuestion projectQuestion, String contents, LocalDateTime createdAt, Boolean temp) {
        this.project = project;
        this.projectQuestion = projectQuestion;
        this.contents = contents;
        this.createdAt = createdAt;
        this.temp = temp;
    }
}
