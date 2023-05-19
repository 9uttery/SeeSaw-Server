package com._8attery.seesaw.domain.value;

import com._8attery.seesaw.domain.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ValueRepository extends JpaRepository<Value, Long> {

    // 사용자 가치 선택
    @Modifying(clearAutomatically = true)
    @Query(value = "insert into ss_value(created_at, value_name, user_id) values(:currentDateTime, :value, :userId)" , nativeQuery = true)
    int setUser3Values(@Param("value") String value, @Param("currentDateTime") LocalDateTime currentDateTime, @Param("userId") Long userId);

    @Query(value = "select count(*) from ss_value where user_id=:userId", nativeQuery = true)
    int checkValuesExist(@Param("userId") Long userId);

    // 사용자 가치 조회
    @Query(value = "select value_id, value_name from ss_value where user_id=:userId", nativeQuery = true)
    List<Object[]> getUser3Values(@Param("userId") Long userId);


    // 가치에 맞는 프로젝트, 고속 충전 조회
    @Query(value = "select p from Project p where p.value.id=:valueId")
    List<Project> findValueProject(@Param("valueId") Long valueId);

    @Query(value = "select count(*) from ss_charge where value_id=:valueId", nativeQuery = true)
    Integer findValueCharge(@Param("valueId") Long valueId);

}
