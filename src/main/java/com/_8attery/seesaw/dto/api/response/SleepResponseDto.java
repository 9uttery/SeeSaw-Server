package com._8attery.seesaw.dto.api.response;

import lombok.*;

@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SleepResponseDto {
    private Integer day;
    private Integer sleep;
    private Integer color; // 색 기준에 따라 0,1,2,3
}
