package com._8attery.seesaw.dto.api.response;

import com._8attery.seesaw.domain.project.Intensity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
