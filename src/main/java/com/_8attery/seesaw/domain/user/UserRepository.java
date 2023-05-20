package com._8attery.seesaw.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    
    Optional<User> findByEmail(String email);

    // 닉네임 수정
    @Modifying(clearAutomatically = true)
    @Query(value = "update ss_user set nick_name=:nickName where user_id=:userId", nativeQuery = true)
    void updateUserNickname(@Param("userId") Long userId, @Param("nickName") String nickName);

    @Query(value = "select nick_name from ss_user where user_id=:userId", nativeQuery = true)
    String findUserNickname(@Param("userId") Long userId);

    // 회원 탈퇴 화면 - 함께 한 일수, 가치 조회
    @Query(value = "select date(created_at) from ss_user where user_id=:userId", nativeQuery = true)
    LocalDate findUserCreatedAt(@Param("userId") Long userId);

    @Query(value = "select distinct value_name from ss_value where user_id=:userId", nativeQuery = true)
    List<String> findValues(@Param("userId") Long userId);

}
