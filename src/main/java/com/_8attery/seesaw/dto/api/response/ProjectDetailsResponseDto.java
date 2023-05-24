package com._8attery.seesaw.dto.api.response;

import com._8attery.seesaw.domain.project.Intensity;
import com._8attery.seesaw.service.value.ValueService;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ProjectDetailsResponseDto {
    @JsonProperty("project_id")
    private Long projectId;
    @JsonProperty("project_name")
    private String projectName;
    @Enumerated(EnumType.STRING)
    @JsonProperty("intensity")
    private Intensity intensity;
    @JsonProperty("goal")
    private String goal;
    @JsonProperty("started_at")
    private LocalDateTime startedAt;
    @JsonProperty("ended_at")
    private LocalDateTime endedAt;
    @JsonProperty("progress_rate")
    private Double progressRate;
    @JsonProperty("is_half_progressed")
    private Boolean isHalfProgressed;
    @JsonProperty("middle_remembrance_id")
    private Long middleRemembranceId;
    @JsonProperty("is_finished")
    private Boolean isFinished;
    @JsonProperty("final_remembrance_id")
    private Long finalRemembranceId;
    @JsonProperty("value_id")
    private Long valueId;
    @JsonProperty("value_name")
    private String valueName;
    @JsonProperty("like_cnt")
    private Integer likeCnt;
    @JsonProperty("nice_cnt")
    private Integer niceCnt;
    @JsonProperty("idk_cnt")
    private Integer idkCnt;
    @JsonProperty("angry_cnt")
    private Integer angryCnt;
    @JsonProperty("sad_cnt")
    private Integer sadCnt;

    public ProjectDetailsResponseDto(Long projectId, String projectName, Intensity intensity, String goal, LocalDateTime startedAt, LocalDateTime endedAt, Boolean isHalfProgressed, Long middleRemembranceId, Boolean isFinished, Long finalRemembranceId, Long valueId, String valueName, Integer likeCnt, Integer niceCnt, Integer idkCnt, Integer angryCnt, Integer sadCnt) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.intensity = intensity;
        this.goal = goal;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
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
