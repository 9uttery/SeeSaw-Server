package com._8attery.seesaw.dto.api.response;

import com._8attery.seesaw.domain.project_emotion.Emotion;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InitialProjectReportResponseDto {

    ProjectReportInfoDto projectReportInfoDto;
    Emotion emotion1;
    Emotion emotion2;
    ProjectMiddleFinalQnaDto projectMiddleFinalQnaDto;
    Long todayAnswerCount;
    Long recordAnswerCount;
    List<SimpleRecordInfo> simpleRecordInfo;

    @Builder
    public InitialProjectReportResponseDto(ProjectReportInfoDto projectReportInfoDto, Emotion emotion1, Emotion emotion2, ProjectMiddleFinalQnaDto projectMiddleFinalQnaDto, Long todayAnswerCount, Long recordAnswerCount, List<SimpleRecordInfo> simpleRecordInfo) {
        this.projectReportInfoDto = projectReportInfoDto;
        this.emotion1 = emotion1;
        this.emotion2 = emotion2;
        this.projectMiddleFinalQnaDto = projectMiddleFinalQnaDto;
        this.todayAnswerCount = todayAnswerCount;
        this.recordAnswerCount = recordAnswerCount;
        this.simpleRecordInfo = simpleRecordInfo;
    }

    @Data
    @NoArgsConstructor
    public static class SimpleRecordInfo {
        private LocalDate createdAt;
        private String recordContent;

        @Builder
        public SimpleRecordInfo(LocalDateTime createdAt, String recordContent) {
            this.createdAt = createdAt.toLocalDate();
            this.recordContent = recordContent;
        }
    }

}
