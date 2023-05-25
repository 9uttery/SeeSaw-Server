package com._8attery.seesaw.domain.project_qna;

import com._8attery.seesaw.dto.api.response.ProjectQnaResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com._8attery.seesaw.domain.project_qna.QProjectQna.projectQna;
import static com._8attery.seesaw.domain.project_question.QProjectQuestion.projectQuestion;
import static com._8attery.seesaw.domain.project_remembrance.QProjectRemembrance.projectRemembrance;

@RequiredArgsConstructor
@Repository
public class ProjectQnaRepositoryCustomImpl implements ProjectQnaRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public ProjectQnaResponseDto findProjectQnaByProjectQnaId(Long projectQnaId) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                ProjectQnaResponseDto.class,
                                projectQna.id,
                                projectQuestion.id,
                                projectQuestion.contents,
                                projectQna.answerChoice,
                                projectQna.answerContent
                        )
                )
                .from(projectQna)
                .join(projectQuestion).on(projectQna.projectQuestion.id.eq(projectQuestion.id))
                .fetchJoin()
                .where(projectQna.id.eq(projectQnaId))
                .fetchOne();
    }

    @Override
    public ProjectQna findProjectQnaByProjectRemembranceAndQuestionId(Long projectRemembranceId, Long questionId) {
        return jpaQueryFactory
                .select(projectQna)
                .from(projectQna)
                .join(projectRemembrance).on(projectQna.projectRemembrance.id.eq(projectRemembrance.id))
                .fetchJoin()
                .join(projectQuestion).on(projectQna.projectQuestion.id.eq(projectQuestion.id))
                .fetchJoin()
                .where(projectQuestion.id.eq(questionId), projectRemembrance.id.eq(projectRemembranceId))
                .fetchOne();
    }

    @Override
    public List<String> findProjectQnaContentsListByProjectRemembranceId(Long projectRemembranceId) {
        return jpaQueryFactory
                .select(projectQna.answerContent)
                .from(projectQna)
                .join(projectRemembrance).on(projectQna.projectRemembrance.id.eq(projectRemembrance.id))
                .where(projectRemembrance.id.eq(projectRemembranceId))
                .fetch();
    }


}
