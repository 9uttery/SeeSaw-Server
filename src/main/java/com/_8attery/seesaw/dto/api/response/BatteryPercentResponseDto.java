package com._8attery.seesaw.dto.api.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatteryPercentResponseDto {

    private LocalDate date; // 날짜
    private Integer batteryPercentage; // 배터리 퍼센트

    public BatteryPercentResponseDto(LocalDateTime date, Integer batteryPercentage) {
        this.date = date.toLocalDate();
        this.batteryPercentage = batteryPercentage;
    }
}
