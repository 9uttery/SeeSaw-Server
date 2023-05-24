package com._8attery.seesaw.dto.api.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectQnaResponseDto {
    private Long projectQnaId;
    private Long projectQuestionId;
    private String questionContent;
    private Long answerChoice;
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
