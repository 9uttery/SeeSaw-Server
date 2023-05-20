package com._8attery.seesaw.dto.api.request;

import com._8attery.seesaw.domain.project_remembrance.RemembranceType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectRemembranceRequestDto {
    @NotNull
    RemembranceType type;
    @JsonProperty("project_id")
    @NotNull
    @Positive
    private Long projectId;
}
