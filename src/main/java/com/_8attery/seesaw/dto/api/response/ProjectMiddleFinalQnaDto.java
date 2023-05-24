package com._8attery.seesaw.dto.api.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectMiddleFinalQnaDto {

    private String middleAnswer;
    private String finalAnswer;

    @Builder
    public ProjectMiddleFinalQnaDto(String middleAnswer, String finalAnswer) {
        this.middleAnswer = middleAnswer;
        this.finalAnswer = finalAnswer;
    }
}
