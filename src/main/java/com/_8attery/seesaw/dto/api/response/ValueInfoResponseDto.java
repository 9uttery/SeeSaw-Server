package com._8attery.seesaw.dto.api.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValueInfoResponseDto {
    public List<ValueProject> projectList;
    private Integer count; // 고속 충전 횟수

    @Getter
    @Setter
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ValueProject {
        private String projectName;
        private Double progressRate;
    }
}
