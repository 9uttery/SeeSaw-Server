package com._8attery.seesaw.dto.api.response;

import lombok.*;

@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SleepDto {
    private Integer day;
    private Integer sleep;
}
