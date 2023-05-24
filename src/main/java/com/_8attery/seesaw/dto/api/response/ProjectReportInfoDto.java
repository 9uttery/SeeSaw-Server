package com._8attery.seesaw.dto.api.response;

import com._8attery.seesaw.domain.project.Intensity;
import com._8attery.seesaw.service.util.ServiceUtils;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectReportInfoDto {
    private String value;
    private String projectName;
    private String startedAt;
    private String endedAt;
    @Enumerated(value = javax.persistence.EnumType.STRING)
    private Intensity projectIntensity;
    private String projectGoal;
    private List<String> jointProject;

    public ProjectReportInfoDto(String value, String projectName, LocalDateTime startedAt, LocalDateTime endedAt, Intensity projectIntensity, String projectGoal) {
        this.value = value;
        this.projectName = projectName;
        this.startedAt = ServiceUtils.LocalDateTimetoLocalDateString(startedAt);
        this.endedAt = ServiceUtils.LocalDateTimetoLocalDateString(endedAt);
        this.projectIntensity = projectIntensity;
        this.projectGoal = projectGoal;
    }
}
