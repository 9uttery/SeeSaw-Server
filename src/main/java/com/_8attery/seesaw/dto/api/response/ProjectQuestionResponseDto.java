package com._8attery.seesaw.dto.api.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectQuestionResponseDto {
    private String contents;

    @Builder
    public ProjectQuestionResponseDto(String contents) {
        this.contents = contents;
    }
}
