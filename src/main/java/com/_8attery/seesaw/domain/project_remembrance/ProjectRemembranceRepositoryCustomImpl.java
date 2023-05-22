package com._8attery.seesaw.domain.project_remembrance;

import com._8attery.seesaw.dto.api.response.ProjectRemembranceResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com._8attery.seesaw.domain.project_qna.QProjectQna.projectQna;
import static com._8attery.seesaw.domain.project_remembrance.QProjectRemembrance.projectRemembrance;

@RequiredArgsConstructor
@Repository
public class ProjectRemembranceRepositoryCustomImpl implements ProjectRemembranceRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public List<ProjectRemembranceResponseDto.QnaDto> findQnaListByRemembranceId(Long projectRemembranceId) {
        return jpaQueryFactory.select(
                        Projections.constructor(
                                ProjectRemembranceResponseDto.QnaDto.class,
                                projectQna.id, projectQna.projectQuestion.contents, projectQna.answerContent
                        )
                )
                .from(projectQna)
                .join(projectRemembrance).on(projectQna.projectRemembrance.id.eq(projectRemembrance.id))
                .where(projectRemembrance.id.eq(projectRemembranceId))
                .fetch();
    }

    @Override
    public Boolean existsByProjectIdAndType(Long projectId, RemembranceType type) {
        return jpaQueryFactory
                .select(projectRemembrance)
                .from(projectRemembrance)
                .where(projectRemembrance.project.id.eq(projectId)
                        .and(projectRemembrance.type.eq(type)))
                .stream().findFirst().isPresent();
    }
}
