package com._8attery.seesaw.domain.battery;

import com._8attery.seesaw.dto.api.response.BatteryPercentResponseDto;
import com._8attery.seesaw.dto.api.response.BatteryVariationResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

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
            "and b1.createdAt between :startDate and :endDate ")
    List<BatteryPercentResponseDto> findUserBatteryHistory(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // 배터리 증감 조회 (30일 증감 내역)
    @Query(value = "select distinct new com._8attery.seesaw.dto.api.response.BatteryVariationResponseDto(b1.createdAt, c.name, c.value.id, b1.sleepTime, b2.sleepGoal, b1.activity, b2.activityGoal) " +
            "from BatteryHistory b1 " +
            "JOIN b1.battery b2 " +
            "JOIN Charge c ON TRUNC(c.createdAt) = TRUNC(b1.createdAt) " +
            "where b2.user.id=:userId " +
            "and b1.createdAt between :startDate and :endDate")
    List<BatteryVariationResponseDto> findUserBatteryVariation(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}
