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
public class BatteryChargeVariationDto {
    private LocalDate date; // 날짜
    private String chargeName;
    private String valueName;
    private Integer chargeVariation; // 고속충전 증감량

    public BatteryChargeVariationDto(LocalDateTime date, String chargeName, String valueName, Integer chargeVariation) {
        this.date = date.toLocalDate();
        this.chargeName = chargeName;
        this.valueName = valueName;
        this.chargeVariation = chargeVariation;
    }
}
