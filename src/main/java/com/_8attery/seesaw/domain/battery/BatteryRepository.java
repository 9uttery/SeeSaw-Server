package com._8attery.seesaw.domain.battery;

import com._8attery.seesaw.dto.api.response.BatteryPercentResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
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

    @Query(value = "select cur_sleep from ss_battery where user_id=:userId", nativeQuery = true)
    Integer findUserCurSleep(@Param("userId") Long userId);

    // 배터리 수준 조회 (7일 퍼센트)
    @Query(value = "select distinct new com._8attery.seesaw.dto.api.response.BatteryPercentResponseDto(b1.createdAt, b1.batteryPercentage) from BatteryHistory b1 join b1.battery b2 " +
            "where b2.user.id=:userId " +
            "and b1.createdAt between :startDate and :endDate ")
    List<BatteryPercentResponseDto> findUserBatteryHistory(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}
