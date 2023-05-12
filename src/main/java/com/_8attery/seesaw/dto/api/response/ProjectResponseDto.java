package com._8attery.seesaw.dto.api.response;

import com._8attery.seesaw.domain.project.Intensity;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponseDto {

    private Long valueId;

    private String projectName; // 프로젝트 이름

    private LocalDateTime startedAt; // 시작 날짜

    private LocalDateTime endedAt; // 종료 날짜

    @Enumerated(EnumType.STRING)
    private Intensity intensity; // 강도

    private String goal; // 목표

    private Boolean isFinished; // 프로젝트 완료 여부

}
