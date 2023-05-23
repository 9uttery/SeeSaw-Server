package com._8attery.seesaw.domain.project;

import com._8attery.seesaw.dto.api.response.ProjectDetailsResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ProjectRepositoryCustom {
    ProjectDetailsResponseDto getProjectDetails(Long projectId);

    List<String> getThreeProjectNamesAtSameTime(Long userId, LocalDateTime startedAt, LocalDateTime endedAt);
}
