package com._8attery.seesaw.domain.project_qna;

import com._8attery.seesaw.domain.project_remembrance.ProjectRemembrance;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SS_PROJECT_QNA")
@Entity
public class ProjectQna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_qna_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "projectRemembrance")
    private ProjectRemembrance projectRemembrance;

    @Column(name = "question_id", nullable = false)
    private Integer questionId; // 질문 번호

    @Column(name = "answer_content")
    private String answerContent; // 답변 내용


    @Builder
    public ProjectQna(Integer questionId, String answerContent) {
        this.questionId = questionId;
        this.answerContent = answerContent;
    }
}
