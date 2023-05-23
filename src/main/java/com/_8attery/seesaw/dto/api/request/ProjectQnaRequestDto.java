package com._8attery.seesaw.dto.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectQnaRequestDto {
    @JsonProperty("project_qna_id")
    @NotNull
    @Positive
    private Long projectQnaId;
    @JsonProperty("answer_choice")
    private Long answerChoice;
    @JsonProperty("answer_content")
    private String answerContent;
}
