package com._8attery.seesaw.domain.project_record;

import com._8attery.seesaw.dto.api.response.InitialProjectReportResponseDto;
import com._8attery.seesaw.dto.api.response.ProjectRecordResponseDto;

import java.util.List;

public interface ProjectRecordRepositoryCustom {
    List<ProjectRecordResponseDto> findAllRecordsByProjectId(Long projectId);

    List<ProjectRecord> findAllByProjectRecordIdList(Long userId, List<Long> projectRecordIdList);

    Long countAllByProjectAndQuestionExists(Long projectId, Boolean questionExists);

    List<InitialProjectReportResponseDto.SimpleRecordInfo> findThreeRandomSimpleRecordByProjectId(Long projectId);
}
