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
    private LocalDate date;
    private Integer curSleep;
    private Integer goalSleep;
    private Integer sleepVariation;

    private String chargeName;
    private String valueName;
    private Integer chargeVariation;

    private Integer curActivity;
    private Integer goalActivity;
    private Integer activityVariation;

    public BatteryVariationResponseDto(LocalDateTime date, Integer curSleep, Integer goalSleep, Integer sleepVariation, Integer curActivity, Integer goalActivity, Integer activityVariation, String chargeName, String valueName, Integer chargeVariation) {
        this.date = date.toLocalDate();
        this.curSleep = curSleep;
        this.goalSleep = goalSleep;
        this.sleepVariation = sleepVariation;
        this.curActivity = curActivity;
        this.goalActivity = goalActivity;
        this.activityVariation = activityVariation;
        this.chargeName = chargeName;
        this.valueName = valueName;
        this.chargeVariation = chargeVariation;
    }
}

