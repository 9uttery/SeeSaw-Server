package com._8attery.seesaw.domain.battery;

import com._8attery.seesaw.domain.charge.Charge;
import com._8attery.seesaw.dto.api.response.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BatteryRepository extends JpaRepository<Battery, Long> {

    // 활동량 목표 설정
    @Modifying(clearAutomatically = true)
    @Query(value = "update ss_battery set activity_goal=:req where user_id=:userId", nativeQuery = true)
    void addUserActivityGoal(@Param("userId") Long userId, @Param("req") Integer req);

    @Query(value = "select activity_goal from ss_battery where user_id=:userId", nativeQuery = true)
    Integer findUserActivityGoal(@Param("userId") Long userId);

    // 수면량 목표 설정
    @Modifying(clearAutomatically = true)
    @Query(value = "update ss_battery set sleep_goal=:req where user_id=:userId", nativeQuery = true)
    void addUserSleepGoal(@Param("userId") Long userId, @Param("req") Integer req);

    @Query(value = "select sleep_goal from ss_battery where user_id=:userId", nativeQuery = true)
    Integer findUserSleepGoal(@Param("userId") Long userId);

    // 현재 활동량 설정
    @Modifying(clearAutomatically = true)
    @Query(value = "update ss_battery set cur_activity=:req where user_id=:userId", nativeQuery = true)
    void addUserCurActivity(@Param("userId") Long userId, @Param("req") Integer req);

    @Query(value = "select cur_activity from ss_battery where user_id=:userId", nativeQuery = true)
    Integer findUserCurActivity(@Param("userId") Long userId);

    // 오늘 수면량 설정
    @Modifying(clearAutomatically = true)
    @Query(value = "update ss_battery set cur_sleep=:req where user_id=:userId", nativeQuery = true)
    void addUserCurSleep(@Param("userId") Long userId, @Param("req") Integer req);

    // 사용자 배터리 갱신
    @Modifying(clearAutomatically = true)
    @Query(value = "update ss_battery set cur_battery = cur_battery + :variation where user_id=:userId", nativeQuery = true)
    void updateCurBattery(@Param("userId") Long userId, @Param("variation") Integer variation);

    // 배터리 증감 내역 레코드 추가
    @Query(value = "select battery_id from ss_battery where user_id=:userId", nativeQuery = true)
    Long findUserBatteryId(@Param("userId") Long userId);

    @Modifying(clearAutomatically = true)
    @Query(value = "insert into ss_battery_variation(created_at, type, variation_percentage, battery_id) values(:createdAt, :type, :variation, :batteryId)", nativeQuery = true)
    void addUserSleepVariation(@Param("batteryId") Long batteryId, @Param("createdAt") LocalDateTime createdAt, @Param("type") String type, @Param("variation") Integer variation);


    @Query(value = "select cur_sleep from ss_battery where user_id=:userId", nativeQuery = true)
    Integer findUserCurSleep(@Param("userId") Long userId);

    // 배터리 수준 조회 (7일 퍼센트)
    @Query(value = "select distinct new com._8attery.seesaw.dto.api.response.BatteryPercentResponseDto(b1.createdAt, b1.batteryPercentage) from BatteryHistory b1 join b1.battery b2 " +
            "where b2.user.id=:userId " +
            "and b1.createdAt between :startDate and :endDate " +
            "order by b1.createdAt desc")
    List<BatteryPercentResponseDto> findUserBatteryHistory(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // 배터리 증감 조회 (30일 증감 내역)
    @Query(value = "select DISTINCT new com._8attery.seesaw.dto.api.response.BatteryDataVariationDto(b1.createdAt, b1.sleepTime, b1.sleepGoal, " +
            "(select b3.variationPercentage from BatteryVariation b3 where b3.type = 'SLEEP' and DATE(b3.createdAt) = DATE(b1.createdAt) and b3.battery.id = :batteryId), " +
            "b1.activity, b1.activityGoal, " +
            "(select b2.variationPercentage from BatteryVariation b2 where b2.type = 'ACTIVITY' and DATE(b2.createdAt) = DATE(b1.createdAt and b2.battery.id = :batteryId ))) " +
            "from BatteryHistory b1 " +
            "where b1.battery.id = :batteryId " +
            "and b1.createdAt between :startDate and :endDate " +
            "order by b1.createdAt desc")
    List<BatteryDataVariationDto> findUserBatteryVariation(@Param("batteryId") Long batteryId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query(value = "select DISTINCT new com._8attery.seesaw.dto.api.response.BatteryChargeVariationDto(c.createdAt, c.name, v.valueName, 10) " +
            "from Charge c join c.value v " +
            "where c.user.id=:userId " +
            "and c.createdAt between :startDate and :endDate " +
            "order by c.createdAt desc")
    List<BatteryChargeVariationDto> findUserBatteryCharge(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);


    // 특정 기간 동안의 활동 내역 조회
    @Query(value = "select DISTINCT new com._8attery.seesaw.dto.api.response.ActivityDto(EXTRACT(DAY FROM b.createdAt), b.activity) from BatteryHistory b " +
            "where EXTRACT(YEAR FROM b.createdAt) = :year and EXTRACT(MONTH FROM b.createdAt) = :month and b.battery.id=:batteryId")
    List<ActivityDto> findUserActivity(@Param("batteryId") Long batteryId, @Param("year") Integer year, @Param("month") Integer month);


    // 특정 기간 동안의 수면 내역 조회
    @Query(value = "select DISTINCT new com._8attery.seesaw.dto.api.response.SleepDto(EXTRACT(DAY FROM b.createdAt), b.sleepTime) from BatteryHistory b " +
            "where EXTRACT(YEAR FROM b.createdAt) = :year and EXTRACT(MONTH FROM b.createdAt) = :month and b.battery.id=:batteryId")
    List<SleepDto> findUserSleep(@Param("batteryId") Long batteryId, @Param("year") Integer year, @Param("month") Integer month);

    // 현재 배터리 상태 조회 -> 현재 배터리 잔량, 고속충전/활동량/수면 현황
    @Query(value = "select b from Battery b where b.user.id=:userId")
    Battery findUserBattery(@Param("userId") Long userId);

    @Query(value = "select c from Charge c where c.user.id=:userId and date(c.createdAt)=curdate()")
    Optional<Charge> findUserCharge(@Param("userId") Long userId);

    @Query(value = "select value_name from ss_value where value_id=:valueId", nativeQuery = true)
    String findUserValue(@Param("valueId") Long valueId);
}
