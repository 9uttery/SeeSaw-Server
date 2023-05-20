package com._8attery.seesaw.dto.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectRecordRequestDto {
    @NotNull
    @Positive
    @JsonProperty("project_id")
    private Long projectId;
    @JsonProperty("question_id")
    private Long projectQuestionId;
    @NotNull
    private String contents;
    @NotNull
    private Boolean temp;
}
