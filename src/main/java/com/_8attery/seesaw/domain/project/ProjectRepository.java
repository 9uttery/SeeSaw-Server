package com._8attery.seesaw.domain.project;

import com._8attery.seesaw.dto.api.response.ProjectCardResponseDto;
import com._8attery.seesaw.dto.api.response.ProjectResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {


    @Modifying(clearAutomatically = true)
    @Query(value = "insert into ss_project(user_id, value_id, started_at, ended_at, project_name, intensity, goal) values(:userId, :valueId, :startedAt, :endedAt, :projectName, :intensity, :goal)", nativeQuery = true)
    void addIngProject(@Param("userId") Long userId, @Param("valueId") Long valueId, @Param("projectName") String projectName, @Param("startedAt") LocalDateTime startedAt, @Param("endedAt") LocalDateTime endedAt, @Param("intensity") String intensity, @Param("goal") String goal);

    @Query(value = "select new com._8attery.seesaw.dto.api.response.ProjectResponseDto(p.value.id, p.projectName, p.startedAt, p.endedAt, p.intensity, p.goal, p.isFinished) from Project p where p.user.id=:userId and p.value.id=:valueId and p.projectName=:projectName")
    Optional<ProjectResponseDto> getProject(@Param("userId") Long userId, @Param("valueId") Long valueId, @Param("projectName") String projectName);

    @Query(value = "select new com._8attery.seesaw.dto.api.response.ProjectCardResponseDto(p.id, p.projectName, p.startedAt, p.endedAt, p.intensity, v.valueName) from Project p join p.value v where p.user.id=:userId and p.isFinished=false")
    List<ProjectCardResponseDto> findProgressProjectList(@Param("userId") Long userId);

    @Query(value = "select new com._8attery.seesaw.dto.api.response.ProjectCardResponseDto(p.id, p.projectName, p.startedAt, p.endedAt, p.intensity, v.valueName) from Project p join p.value v where p.user.id=:userId and p.isFinished=true")
    List<ProjectCardResponseDto> findCompleteProjectList(@Param("userId") Long userId);
}
