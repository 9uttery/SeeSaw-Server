package com._8attery.seesaw.dto.api.response;

import com._8attery.seesaw.domain.project_emotion.Emotion;
import com._8attery.seesaw.domain.project_remembrance.RemembranceType;
import lombok.*;

import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InitialProjectReportResponseDto {

    private ProjectReportInfoDto projectReportInfoDto;
    private Emotion emotion1;
    private Emotion emotion2;
    private ProjectMiddleFinalQnaDto projectMiddleFinalQnaDto;
    private Long todayAnswerCount;
    private Long recordAnswerCount;
    private List<SimpleRecordInfo> simpleRecordInfo;
    private List<AnswerContentDto> answerContentList;

    @Builder
    public InitialProjectReportResponseDto(ProjectReportInfoDto projectReportInfoDto, Emotion emotion1, Emotion emotion2, ProjectMiddleFinalQnaDto projectMiddleFinalQnaDto, Long todayAnswerCount, Long recordAnswerCount, List<SimpleRecordInfo> simpleRecordInfo, List<AnswerContentDto> answerContentList) {
        this.projectReportInfoDto = projectReportInfoDto;
        this.emotion1 = emotion1;
        this.emotion2 = emotion2;
        this.projectMiddleFinalQnaDto = projectMiddleFinalQnaDto;
        this.todayAnswerCount = todayAnswerCount;
        this.recordAnswerCount = recordAnswerCount;
        this.simpleRecordInfo = simpleRecordInfo;
        this.answerContentList = answerContentList;
    }

    @Data
    @NoArgsConstructor
    public static class SimpleRecordInfo {
        private LocalDate createdAt;
        private String content;

        @Builder
        public SimpleRecordInfo(LocalDateTime createdAt, String content) {
            this.createdAt = createdAt.toLocalDate();
            this.content = content;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AnswerContentDto {
        @Enumerated(javax.persistence.EnumType.STRING)
        private RemembranceType remembranceType;
        private Long choice;
        private String content;
    }

}
