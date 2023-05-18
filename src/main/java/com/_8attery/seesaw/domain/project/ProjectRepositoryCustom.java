package com._8attery.seesaw.domain.project;

import com._8attery.seesaw.dto.api.response.ProjectDetailsResponseDto;

public interface ProjectRepositoryCustom {
    ProjectDetailsResponseDto getProjectDetails(Long projectId);
}
