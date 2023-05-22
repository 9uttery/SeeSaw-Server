package com._8attery.seesaw.domain.project_remembrance;

import com._8attery.seesaw.dto.api.response.ProjectRemembranceResponseDto;

import java.util.List;

public interface ProjectRemembranceRepositoryCustom {
    List<ProjectRemembranceResponseDto.QnaDto> findQnaListByRemembranceId(Long projectRemembranceId);
}
