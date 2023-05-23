package com._8attery.seesaw.domain.project_emotion;

import com._8attery.seesaw.dto.api.request.ProjectEmotionRequestDto;
import com._8attery.seesaw.dto.api.response.ProjectEmotionResponseDto;

public interface ProjectEmotionRepositoryCustom {
    ProjectEmotionResponseDto updateProjectEmotion(ProjectEmotionRequestDto projectEmotionRequestDto);

    ProjectEmotion findProjectEmotionByProjectId(Long projectId);
}
