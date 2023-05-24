package com._8attery.seesaw.domain.project;

import com._8attery.seesaw.dto.api.response.ProjectDetailsResponseDto;
import com._8attery.seesaw.dto.api.response.ProjectReportInfoDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ProjectRepositoryCustom {
    ProjectDetailsResponseDto getProjectDetails(Long projectId);

    List<String> getJointProject(Long userId, Long projectId, LocalDateTime startedAt, LocalDateTime endedAt);

    ProjectReportInfoDto getProjectReportInfo(Long projectId);
}
