package com._8attery.seesaw.domain.project_qna;

import com._8attery.seesaw.dto.api.response.ProjectQnaResponseDto;

import java.util.List;

public interface ProjectQnaRepositoryCustom {
    ProjectQnaResponseDto findProjectQnaByProjectQnaId(Long projectQnaId);

    ProjectQna findProjectQnaByProjectRemembranceAndQuestionId(Long projectRemembranceId, Long questionId);

    List<String> findProjectQnaContentsListByProjectRemembranceId(Long projectRemembranceId);
}
