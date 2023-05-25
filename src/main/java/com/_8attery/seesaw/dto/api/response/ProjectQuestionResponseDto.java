package com._8attery.seesaw.dto.api.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectQuestionResponseDto {
    private Long id;
    private String contents;

    @Builder
    public ProjectQuestionResponseDto(Long id, String contents) {
        this.id = id;
        this.contents = contents;
    }
}
