package com._8attery.seesaw.domain.auth;

import com._8attery.seesaw.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SignupRepository extends JpaRepository<User, Long> {

    @Query(value = "select count(email) from ss_user where user_id=:userId", nativeQuery = true)
    int checkEmailExist(@Param("userId") Long userId);

    @Modifying(clearAutomatically = true)
    @Query(value = "update ss_user set agree_marketing=:agreeMarketing where user_id=:userId", nativeQuery = true)
    void setAgreeMarketing(@Param("agreeMarketing") Boolean agreeMarketing, @Param("userId") Long userId);

    @Modifying(clearAutomatically = true)
    @Query(value = "update ss_user set nick_name=:nickName where user_id=:userId", nativeQuery = true)
    void setNickName(@Param("nickName") String nickName, @Param("userId") Long userId);

    @Modifying(clearAutomatically = true)
    @Query(value = "update ss_user set email=:email where user_id=:userId", nativeQuery = true)
    void setEmail(@Param("email") String email, @Param("userId") Long userId);

    @Query(value = "select agree_marketing, nick_name from ss_user where user_id=:userId", nativeQuery = true)
    List<Object[]> getInfo(@Param("userId") Long userId);

    @Modifying(clearAutomatically = true)
    @Query(value = "insert into ss_battery(cur_battery, activity_goal, sleep_goal, user_id) values(80, 200, 6, :userId)", nativeQuery = true)
    void setBattery(@Param("userId") Long userId);
}
