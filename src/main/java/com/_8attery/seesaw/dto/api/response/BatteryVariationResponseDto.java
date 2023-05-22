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

    private Sleep sleep;
    private Charge charge;
    private Activity activity;

    @Getter
    @Setter
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Sleep {
        private Integer curSleep;
        private Integer goalSleep;
        private Integer sleepVariation;
    }

    @Getter
    @Setter
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Charge {
        private String chargeName;
        private String valueName;
        private Integer chargeVariation;
    }

    @Getter
    @Setter
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Activity {
        private Integer curActivity;
        private Integer goalActivity;
        private Integer activityVariation;
    }

    public BatteryVariationResponseDto(LocalDateTime date, Sleep sleep, Charge charge, Activity activity) {
        this.date = date.toLocalDate();
        this.sleep = sleep;
        this.charge = charge;
        this.activity = activity;
    }
    
}

