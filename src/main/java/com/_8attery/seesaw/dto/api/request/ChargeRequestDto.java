package com._8attery.seesaw.dto.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChargeRequestDto {

    private Long valueId; // 가치 id

    private String chargeName; // 고속충전 타이틀

    private LocalDateTime createdAt; // 생성날짜
}
