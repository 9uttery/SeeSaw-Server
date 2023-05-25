package com._8attery.seesaw.domain.battery;

import com._8attery.seesaw.dto.api.response.ProjectReportBatteryResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import static com._8attery.seesaw.domain.battery.QBattery.battery;
import static com._8attery.seesaw.domain.battery_history.QBatteryHistory.batteryHistory;

@RequiredArgsConstructor
@Repository
public class BatteryRepositoryCustomImpl implements BatteryRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public ProjectReportBatteryResponseDto getProjectReportBatteryByTerm(Long batteryId, LocalDateTime startedAt, LocalDateTime endedAt) {
        ProjectReportBatteryResponseDto result = jpaQueryFactory
                .select(
                        Projections.constructor(
                                ProjectReportBatteryResponseDto.class,
                                batteryHistory.batteryPercentage.avg(),
                                batteryHistory.batteryPercentage.max(),
                                batteryHistory.batteryPercentage.min(),
                                batteryHistory.activity.avg(),
                                batteryHistory.sleepTime.avg()
                        )
                )
                .from(batteryHistory)
                .join(battery).on(batteryHistory.battery.id.eq(battery.id))
                .fetchJoin()
                .where(battery.id.eq(batteryId), batteryHistory.createdAt.between(startedAt, endedAt))
                .groupBy(battery.id)
                .fetchOne();


        return result;
    }

    @Override
    public ProjectReportBatteryResponseDto.ChargeReport getChargeReportByTerm(Long batteryId, LocalDateTime startedAt, LocalDateTime endedAt) {
        return null;
    }
}
