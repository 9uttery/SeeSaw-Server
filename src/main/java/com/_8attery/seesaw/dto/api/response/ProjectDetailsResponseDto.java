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
    private Intensity intensity;
    private String goal;
    @JsonProperty("started_at")
    private LocalDateTime startedAt;
    @JsonProperty("ended_at")
    private LocalDateTime endedAt;
    @JsonProperty("is_finished")
    private Boolean isFinished;
    @JsonProperty("is_half_progressed")
    private Boolean isHalfProgressed;
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
