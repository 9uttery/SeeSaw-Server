package com._8attery.seesaw.domain.project_qna;

import com._8attery.seesaw.domain.project_question.ProjectQuestion;
import com._8attery.seesaw.domain.project_remembrance.ProjectRemembrance;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SS_PROJECT_QNA")
@Entity
public class ProjectQna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_qna_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "projectRemembrance", nullable = false)
    private ProjectRemembrance projectRemembrance;

    @ManyToOne
    @JoinColumn(name = "project_question_id", nullable = false)
    private ProjectQuestion projectQuestion; // 질문 id

    @Column(name = "answer_choice")
    private Long answerChoice; // 답변 선택지

    @Column(name = "answer_content")
    private String answerContent; // 답변 내용

    @Builder
    public ProjectQna(ProjectRemembrance projectRemembrance, ProjectQuestion projectQuestion, Long answerChoice, String answerContent) {
        this.projectRemembrance = projectRemembrance;
        this.projectQuestion = projectQuestion;
        this.answerChoice = answerChoice;
        this.answerContent = answerContent;

    }
}
