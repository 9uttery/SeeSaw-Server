package com._8attery.seesaw.domain.value;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ValueRepository extends JpaRepository<Value, Long> {

    @Modifying(clearAutomatically = true)
    @Query(value = "insert into ss_value(created_at, value_name, user_id) values(:currentDateTime, :value, :userId)" , nativeQuery = true)
    int setUser3Values(@Param("value") String value, @Param("currentDateTime") LocalDateTime currentDateTime, @Param("userId") Long userId);

    @Query(value = "select count(*) from ss_value where user_id=:userId", nativeQuery = true)
    int checkValuesExist(@Param("userId") Long userId);

    @Query(value = "select value_id, value_name from ss_value where user_id=:userId", nativeQuery = true)
    List<Object[]> getUser3Values(@Param("userId") Long userId);
}
