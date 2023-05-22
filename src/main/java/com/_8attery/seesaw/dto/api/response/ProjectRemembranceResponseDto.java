package com._8attery.seesaw.dto.api.response;

import com._8attery.seesaw.domain.project_remembrance.RemembranceType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectRemembranceResponseDto {
    @JsonProperty("remembrance_id")
    private Long remembranceId;

    @JsonProperty("type")
    private RemembranceType type;

    @JsonProperty("qna_list")
    private List<QnaDto> qnaList;

    @Builder
    public ProjectRemembranceResponseDto(Long remembranceId, RemembranceType remembranceType, List<QnaDto> qnaList) {
        this.remembranceId = remembranceId;
        this.type = remembranceType;
        this.qnaList = qnaList;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QnaDto {
        @JsonProperty("qna_id")
        private Long qnaId;
        @JsonProperty("question")
        private String question;
        @JsonProperty("answer")
        private String answer;
    }
}
