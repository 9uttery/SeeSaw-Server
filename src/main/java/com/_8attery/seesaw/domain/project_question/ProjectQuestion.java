package com._8attery.seesaw.domain.project_question;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Entity
@Table(name = "SS_PROJECT_QUESTION")
public class ProjectQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_question_id")
    private Long id;
    @Column(name = "question_type")
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;
    @Column(name = "contents")
    private String contents;

    @Builder
    public ProjectQuestion(QuestionType questionType, String contents) {
        this.questionType = questionType;
        this.contents = contents;
    }
}
