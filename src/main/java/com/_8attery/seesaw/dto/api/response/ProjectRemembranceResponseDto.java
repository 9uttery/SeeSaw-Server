package com._8attery.seesaw.dto.api.response;

import com._8attery.seesaw.domain.project_remembrance.RemembranceType;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectRemembranceResponseDto {
    private Long remembranceId;

    private RemembranceType type;

    private List<QnaDto> qnaList;

    @Builder
    public ProjectRemembranceResponseDto(Long remembranceId, RemembranceType remembranceType, List<QnaDto> qnaList, String userName, String emotion) {
        this.remembranceId = remembranceId;
        this.type = remembranceType;
        this.qnaList = qnaList.stream().peek(
                qnaDto -> {
                    qnaDto.setQuestion(qnaDto.question.replace("00님", userName + "님"));
                    qnaDto.setQuestion(qnaDto.question.replace("00감정", emotion));
                }
        ).collect(Collectors.toList());
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
