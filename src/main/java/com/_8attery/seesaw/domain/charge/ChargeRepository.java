package com._8attery.seesaw.domain.charge;

import com._8attery.seesaw.dto.api.response.ChargeResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ChargeRepository extends JpaRepository<Charge, Long> {


    // 고속충전 사용
    @Modifying(clearAutomatically = true)
    @Query(value = "insert into ss_charge(user_id, value_id, name, created_at) values(:userId, :valueId, :chargeName, :createdAt)", nativeQuery = true)
    void addUserCharge(@Param("userId") Long userId, @Param("valueId") Long valueId, @Param("chargeName") String chargeName, @Param("createdAt") LocalDateTime createdAt);

    // 오늘 고속충전 여부 수정
    @Modifying(clearAutomatically = true)
    @Query(value = "update ss_battery set is_charged=true where user_id=:userId", nativeQuery = true)
    void updateIsCharged(@Param("userId") Long userId);

    // 사용자 배터리 + 30
    @Modifying(clearAutomatically = true)
    @Query(value = "update ss_battery set cur_battery = cur_battery + 30 where user_id=:userId", nativeQuery = true)
    void updateUserBattery(@Param("userId") Long userId);

    // 배터리 증감 내역 레코드 추가
    @Query(value = "select battery_id from ss_battery where user_id=:userId", nativeQuery = true)
    Long findUserBattery(@Param("userId") Long userId);

    @Modifying(clearAutomatically = true)
    @Query(value = "insert into ss_battery_variation(created_at, type, variation_percentage, battery_id) values(:createdAt, :type, 30, :batteryId)", nativeQuery = true)
    void addUserVariation(@Param("batteryId") Long batteryId, @Param("createdAt") LocalDateTime createdAt, @Param("type") String type);


    // 고속충전 사용 반환
    @Query(value = "select new com._8attery.seesaw.dto.api.response.ChargeResponseDto(c.value.id, c.name, c.createdAt) from Charge c where c.user.id=:userId and c.value.id=:valueId and c.name = :chargeName and c.createdAt =:createdAt")
    Optional<ChargeResponseDto> findUserCharge(@Param("userId") Long userId, @Param("valueId") Long valueId, @Param("chargeName") String chargeName, @Param("createdAt") LocalDateTime createdAt);

    // 고속충전 조회
    @Query(value = "select new com._8attery.seesaw.dto.api.response.ChargeResponseDto(c.value.id, c.name, c.createdAt) from Charge c " +
            "where c.user.id=:userId " +
            "and c.createdAt between :startDate and :endDate ")
    Optional<ChargeResponseDto> findTodayCharge(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);


}
