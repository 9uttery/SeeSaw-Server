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
public class BatteryVariationResponseDto {

    private LocalDate date; // 날짜
    private Integer curActivity;
    private Integer goalActivity;
    private Integer curSleep;
    private Integer goalSleep;
    private String chargeName;
    private String valueName;

//    public BatteryPercentResponseDto(LocalDateTime date, Integer batteryPercentage) {
//        this.date = date.toLocalDate();
//        this.batteryPercentage = batteryPercentage;
//    }
}
