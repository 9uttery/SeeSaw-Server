package com._8attery.seesaw.dto.api.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
public class ProjectCountResponseDto {

    private Integer progressCount;

    private Integer completeCount;

    public ProjectCountResponseDto(Integer progressCount, Integer completeCount) {
        this.progressCount = progressCount;
        this.completeCount = completeCount;
    }
}
