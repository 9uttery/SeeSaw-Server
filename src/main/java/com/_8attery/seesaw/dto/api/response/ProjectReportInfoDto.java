package com._8attery.seesaw.dto.api.response;

import com._8attery.seesaw.domain.project.Intensity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectReportInfoDto {
    private String value;
    private String projectName;
    private LocalDate startedAt;
    private LocalDate endedAt;
    @Enumerated(value = javax.persistence.EnumType.STRING)
    private Intensity projectIntensity;
    private String projectGoal;
    private List<String> jointProject;

    public ProjectReportInfoDto(String value, String projectName, LocalDateTime startedAt, LocalDateTime endedAt, Intensity projectIntensity, String projectGoal) {
        this.value = value;
        this.projectName = projectName;
        this.startedAt = startedAt.toLocalDate();
        this.endedAt = endedAt.toLocalDate();
        this.projectIntensity = projectIntensity;
        this.projectGoal = projectGoal;
    }
}
