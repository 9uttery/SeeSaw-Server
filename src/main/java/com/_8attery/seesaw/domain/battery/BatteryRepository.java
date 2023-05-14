package com._8attery.seesaw.domain.battery;

import com._8attery.seesaw.domain.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

}
