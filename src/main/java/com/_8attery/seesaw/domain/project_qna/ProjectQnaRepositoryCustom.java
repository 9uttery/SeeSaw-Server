package com._8attery.seesaw.domain.project_qna;

import com._8attery.seesaw.dto.api.response.ProjectQnaResponseDto;

public interface ProjectQnaRepositoryCustom {
    ProjectQnaResponseDto findProjectQnaByProjectQnaId(Long projectQnaId);
}
