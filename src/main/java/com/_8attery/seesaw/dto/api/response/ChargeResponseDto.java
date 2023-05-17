package com._8attery.seesaw.dto.api.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChargeResponseDto {

    // 고민

    private Long valueId; // 가치 id

    private String chargeName; // 고속충전 타이틀

    private LocalDateTime createdAt; // 생성날짜
}
