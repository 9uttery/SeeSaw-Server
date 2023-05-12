package com._8attery.seesaw.dto.api.request;

import com._8attery.seesaw.domain.project.Intensity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequestDto {

    private Long valueId;

    private String projectName; // 프로젝트 이름

    private LocalDateTime startedAt; // 시작 날짜

    private LocalDateTime endedAt; // 종료 날짜

    private String intensity; // 강도

    private String goal; // 목표

}
