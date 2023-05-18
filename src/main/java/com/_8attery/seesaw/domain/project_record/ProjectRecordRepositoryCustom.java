package com._8attery.seesaw.domain.project_record;

import com._8attery.seesaw.dto.api.response.ProjectRecordResponseDto;

import java.util.List;

public interface ProjectRecordRepositoryCustom {
    List<ProjectRecordResponseDto> findAllRecordsByProjectId(Long projectId);
}
