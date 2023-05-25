package com._8attery.seesaw.domain.battery;

import com._8attery.seesaw.dto.api.response.ProjectReportBatteryResponseDto;

import java.time.LocalDateTime;

public interface BatteryRepositoryCustom {
    ProjectReportBatteryResponseDto getProjectReportBatteryByTerm(Long batteryId, LocalDateTime startedAt, LocalDateTime endedAt);

    ProjectReportBatteryResponseDto.ChargeReport getChargeReportByTerm(Long batteryId, LocalDateTime startedAt, LocalDateTime endedAt);
}
