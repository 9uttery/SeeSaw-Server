package com._8attery.seesaw.dto.api.request;

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
    private Long projectId;
    private Long projectQuestionId;
    @NotNull
    private String contents;
    @NotNull
    private Boolean temp;
}
