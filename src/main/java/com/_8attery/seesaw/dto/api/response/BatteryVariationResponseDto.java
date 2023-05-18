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
    private Integer curSleep;
    private Integer goalSleep;
    private Integer sleepVariation; // 수면에 따른 배터리 증감량
    private Integer curActivity;
    private Integer goalActivity;
    private Integer activityVariation; // 활동량에 따른 배터리 증감량

    public BatteryVariationResponseDto(LocalDateTime date, Integer curSleep, Integer goalSleep, Integer sleepVariation, Integer curActivity, Integer goalActivity, Integer activityVariation) {
        this.date = date.toLocalDate();
        this.curSleep = curSleep;
        this.goalSleep = goalSleep;
        this.sleepVariation = sleepVariation;
        this.curActivity = curActivity;
        this.goalActivity = goalActivity;
        this.activityVariation = activityVariation;
    }

}
