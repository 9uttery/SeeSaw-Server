package com._8attery.seesaw.dto.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatteryRequestDto {

    private Integer value; // 목표 수면량, 활동량 / 오늘 수면량, 활동량
}
