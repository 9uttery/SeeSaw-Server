package com._8attery.seesaw.dto.api.response;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
public class ProjectReportBatteryResponseDto {
    private EnergyReport energyReport;
    private ChargeReport chargeReport;
    private HealthReport healthReport;

    public ProjectReportBatteryResponseDto(Double energyAvg, Integer energyMax, Integer energyMin, Double activityAvg, Double sleepAvg) {
        this.energyReport = EnergyReport.builder()
                .avg(energyAvg.longValue())
                .max(energyMax.longValue())
                .min(energyMin.longValue())
                .build();
        this.healthReport = HealthReport.builder()
                .activityAvg(activityAvg.longValue())
                .sleepAvg(sleepAvg.longValue())
                .build();
    }

    public ProjectReportBatteryResponseDto(Long energyAvg, Long energyMax, Long energyMin, List<ValueNameAndCount> valueNameAndCountList, Long activityAvg, Long sleepAvg) {
        this.energyReport = EnergyReport.builder()
                .avg(energyAvg)
                .max(energyMax)
                .min(energyMin)
                .build();
        this.chargeReport = ChargeReport.builder()
                .valueCountList(valueNameAndCountList)
                .build();
        this.healthReport = HealthReport.builder()
                .activityAvg(activityAvg)
                .sleepAvg(sleepAvg)
                .build();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    static class EnergyReport {
        private Long avg;
        private Long max;
        private Long min;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ChargeReport {
        List<ValueNameAndCount> valueCountList;
    }

    @Getter
    @NoArgsConstructor
    public static class ValueNameAndCount {
        private String name;
        private Long count;

        @Builder
        public ValueNameAndCount(String name, Long count) {
            this.name = name;
            this.count = count == null ? 0L : count;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    static class HealthReport {
        private Long activityAvg;
        private Long sleepAvg;
    }
}
