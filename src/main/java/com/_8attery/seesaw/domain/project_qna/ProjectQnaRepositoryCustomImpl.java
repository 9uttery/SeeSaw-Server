package com._8attery.seesaw.domain.project_qna;

import com._8attery.seesaw.dto.api.response.ProjectQnaResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com._8attery.seesaw.domain.project_qna.QProjectQna.projectQna;
import static com._8attery.seesaw.domain.project_question.QProjectQuestion.projectQuestion;

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
}
