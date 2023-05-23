package com._8attery.seesaw.dto.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectQnaResponseDto {
    @JsonProperty("project_qna_id")
    private Long projectQnaId;
    @JsonProperty("project_question_id")
    private Long projectQuestionId;
    @JsonProperty("question_content")
    private String questionContent;
    @JsonProperty("answer_choice")
    private Long answerChoice;
    @JsonProperty("answer_content")
    private String answerContent;

    @Builder
    public ProjectQnaResponseDto(Long projectQnaId, Long projectQuestionId, String questionContent, Long answerChoice, String answerContent) {
        this.projectQnaId = projectQnaId;
        this.projectQuestionId = projectQuestionId;
        this.questionContent = questionContent;
        this.answerChoice = answerChoice;
        this.answerContent = answerContent;
    }

}
