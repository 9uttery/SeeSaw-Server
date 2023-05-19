package com._8attery.seesaw.dto.api.response;

import lombok.*;

@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityResponseDto {
    private Integer day;
    private Integer activity;
    private Integer color; // 색 기준에 따라 0,1,2,3
}
