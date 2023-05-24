package com._8attery.seesaw.dto.api.response;

import com._8attery.seesaw.domain.project_remembrance.RemembranceType;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectRemembranceResponseDto {
    private Long remembranceId;

    private RemembranceType type;

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
        private Long qnaId;
        private String question;
        private Long answerChoice;
        private String answerContent;
    }
}
