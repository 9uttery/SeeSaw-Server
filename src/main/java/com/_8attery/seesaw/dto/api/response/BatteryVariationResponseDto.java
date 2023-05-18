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

    // 수면 -> 고속충전 -> 활동량
    private LocalDate date; // 날짜
    private String chargeName;
//    private String valueName;
    private Long valueId; // 일단 아이디로
    private Integer curSleep;
    private Integer goalSleep;
    private Integer curActivity;
    private Integer goalActivity;

    public BatteryVariationResponseDto(LocalDateTime date, String chargeName, Long valueId, Integer curSleep, Integer goalSleep, Integer curActivity, Integer goalActivity) {
        this.date = date.toLocalDate();
        this.chargeName = chargeName;
        this.valueId = valueId;
        this.curSleep = curSleep;
        this.goalSleep = goalSleep;
        this.curActivity = curActivity;
        this.goalActivity = goalActivity;
    }

}
