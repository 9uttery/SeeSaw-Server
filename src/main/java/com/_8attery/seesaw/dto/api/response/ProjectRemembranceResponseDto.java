package com._8attery.seesaw.dto.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProjectRemembranceResponseDto {
    @JsonProperty("remembrance_id")
    private Long remembranceId;
    @JsonProperty("project_id")
    private List<QnaDto> qnaList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QnaDto {
        @JsonProperty("qna_id")
        private Long qnaId;
        private String question;
        private String answer;
    }
}
