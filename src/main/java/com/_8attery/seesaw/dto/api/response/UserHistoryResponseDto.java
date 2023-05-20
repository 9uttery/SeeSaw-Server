package com._8attery.seesaw.dto.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserHistoryResponseDto {
    private Long dayCount; // 함께한 일수
    private List<String> valueNames; // 가치 목록
}
