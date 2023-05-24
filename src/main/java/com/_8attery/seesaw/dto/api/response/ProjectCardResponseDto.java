package com._8attery.seesaw.dto.api.response;

import com._8attery.seesaw.domain.project.Intensity;
import com._8attery.seesaw.service.value.ValueService;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
public class ProjectCardResponseDto {

    private Long projectId;

    private String projectName;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    private Double progressRate;

    @Enumerated(EnumType.STRING)
    private Intensity intensity; // 강도

    private String valueName;

    public ProjectCardResponseDto(Long projectId, String projectName, LocalDateTime startedAt, LocalDateTime endedAt, Intensity intensity, String valueName) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.progressRate = ValueService.calculateProgressPercentage(startedAt, endedAt, 100.0);
        this.intensity = intensity;
        this.valueName = valueName;
    }
}
