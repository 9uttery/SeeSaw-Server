package com._8attery.seesaw.domain.project;

import com._8attery.seesaw.domain.project_remembrance.RemembranceType;
import com._8attery.seesaw.dto.api.response.ProjectDetailsResponseDto;
import com._8attery.seesaw.dto.api.response.ProjectReportInfoDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com._8attery.seesaw.domain.project.QProject.project;
import static com._8attery.seesaw.domain.project_emotion.QProjectEmotion.projectEmotion;
import static com._8attery.seesaw.domain.project_remembrance.QProjectRemembrance.projectRemembrance;
import static com._8attery.seesaw.domain.value.QValue.value;

@RequiredArgsConstructor
@Repository
public class ProjectRepositoryCustomImpl implements ProjectRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public ProjectDetailsResponseDto getProjectDetails(Long projectId) {
        ProjectDetailsResponseDto result = jpaQueryFactory
                .select(
                        Projections.constructor(ProjectDetailsResponseDto.class,
                                project.id,
                                project.projectName,
                                project.intensity,
                                project.goal,
                                project.startedAt,
                                project.endedAt,
                                project.isFinished,
                                Expressions.as(
                                        JPAExpressions
                                                .select(projectRemembrance.id)
                                                .from(projectRemembrance)
                                                .where(
                                                        projectRemembrance.project.id.eq(projectId),
                                                        projectRemembrance.type.eq(RemembranceType.MIDDLE)
                                                ), "middle_remembrance_id"
                                ),
                                project.isFinished,
                                Expressions.as(
                                        JPAExpressions
                                                .select(projectRemembrance.id)
                                                .from(projectRemembrance)
                                                .where(
                                                        projectRemembrance.project.id.eq(projectId),
                                                        projectRemembrance.type.eq(RemembranceType.FINAL)
                                                ), "final_remembrance_id"
                                ),
                                value.id,
                                value.valueName,
                                projectEmotion.likeCnt,
                                projectEmotion.niceCnt,
                                projectEmotion.idkCnt,
                                projectEmotion.angryCnt,
                                projectEmotion.sadCnt
                        )
                )
                .from(project)
                .leftJoin(value).on(value.id.eq(project.value.id))
                .fetchJoin()
                .leftJoin(projectEmotion).on(project.id.eq(projectEmotion.project.id))
                .fetchJoin()
                .where(project.id.eq(projectId))
                .fetchOne();

        return result;
    }

    @Override
    public List<String> getJointProject(Long userId, Long projectId, LocalDateTime startedAt, LocalDateTime endedAt) {

        return jpaQueryFactory.select(project.projectName)
                .from(project)
                .where(project.user.id.eq(userId), project.id.ne(projectId), project.startedAt.between(startedAt, endedAt).or(project.endedAt.between(startedAt, endedAt)))
                .limit(3)
                .orderBy(project.id.desc())
                .fetch();
    }

    @Override
    public ProjectReportInfoDto getProjectReportInfo(Long projectId) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                ProjectReportInfoDto.class,
                                value.valueName,
                                project.projectName,
                                project.startedAt,
                                project.endedAt,
                                project.intensity,
                                project.goal
                        )
                )
                .from(project)
                .join(value).on(value.id.eq(project.value.id))
                .fetchJoin()
                .where(project.id.eq(projectId))
                .fetchOne();
    }

}
