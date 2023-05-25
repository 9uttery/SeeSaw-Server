package com._8attery.seesaw.dto.api.response;

import com._8attery.seesaw.domain.project.Intensity;
import com._8attery.seesaw.service.util.ServiceUtils;
import com._8attery.seesaw.service.value.ValueService;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor
public class ProjectDetailsResponseDto {
    private Long projectId;
    private String projectName;
    @Enumerated(EnumType.STRING)
    private Intensity intensity;
    private String goal;
    private String startedAt;
    private String halfDate;
    private String endedAt;
    private Double progressRate;
    private Boolean isHalfProgressed;
    private Long middleRemembranceId;
    private Boolean isFinished;
    private Long finalRemembranceId;
    private Long valueId;
    private String valueName;
    private Integer likeCnt;
    private Integer niceCnt;
    private Integer idkCnt;
    private Integer angryCnt;
    private Integer sadCnt;

    public ProjectDetailsResponseDto(Long projectId, String projectName, Intensity intensity, String goal, LocalDateTime startedAt, LocalDateTime endedAt, Boolean isHalfProgressed, Long middleRemembranceId, Boolean isFinished, Long finalRemembranceId, Long valueId, String valueName, Integer likeCnt, Integer niceCnt, Integer idkCnt, Integer angryCnt, Integer sadCnt) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.intensity = intensity;
        this.goal = goal;
        this.startedAt = ServiceUtils.LocalDateTimetoLocalDateString(startedAt);
        this.halfDate = ServiceUtils.LocalDateTimetoLocalDateString(startedAt.plusDays(ChronoUnit.DAYS.between(startedAt, endedAt) / 2));
        this.endedAt = ServiceUtils.LocalDateTimetoLocalDateString(endedAt);
        this.progressRate = ValueService.calculateProgressPercentage(startedAt, endedAt, 100.0);
        this.isHalfProgressed = isHalfProgressed;
        this.middleRemembranceId = middleRemembranceId;
        this.isFinished = isFinished;
        this.finalRemembranceId = finalRemembranceId;
        this.valueId = valueId;
        this.valueName = valueName;
        this.likeCnt = likeCnt;
        this.niceCnt = niceCnt;
        this.idkCnt = idkCnt;
        this.angryCnt = angryCnt;
        this.sadCnt = sadCnt;
    }

}
