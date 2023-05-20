package com._8attery.seesaw.dto.api.response;

import lombok.*;

@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatteryResponseDto {
    private Integer curBattery;

    // 고속충전 여부 false -> chargeName, valueName null 로 반환
    private String chargeName;
    private String valueName;

    private Integer curActivity; // 현재 활동량
    private Integer activityGoal; // 목표 활동량
    private Integer curSleep; // 어제 수면량
    private Integer sleepGoal; // 목표 수면량
}
