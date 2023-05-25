package com._8attery.seesaw.domain.project_record;

import com._8attery.seesaw.dto.api.response.InitialProjectReportResponseDto;
import com._8attery.seesaw.dto.api.response.ProjectRecordResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com._8attery.seesaw.domain.project.QProject.project;
import static com._8attery.seesaw.domain.project_question.QProjectQuestion.projectQuestion;
import static com._8attery.seesaw.domain.project_record.QProjectRecord.projectRecord;

@RequiredArgsConstructor
@Repository
public class ProjectRecordRepositoryCustomImpl implements ProjectRecordRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<ProjectRecordResponseDto> findAllRecordsByProjectId(Long projectId) {
        return jpaQueryFactory.select(Projections.constructor(
                        ProjectRecordResponseDto.class,
                        projectRecord.id,
                        projectRecord.createdAt,
                        projectQuestion.contents,
                        projectRecord.contents)
                )
                .from(projectRecord)
                .leftJoin(projectQuestion).on(projectRecord.projectQuestion.id.eq(projectQuestion.id))
                .fetchJoin()
                .where(projectRecord.project.id.eq(projectId))
                .orderBy(projectRecord.createdAt.desc()) // 나중에 id로 수정 필요
                .fetch();
    }

    @Override
    public List<ProjectRecord> findAllByProjectRecordIdList(Long userId, List<Long> projectRecordIdList) {
        return jpaQueryFactory.selectFrom(projectRecord)
                .where(projectRecord.id.in(projectRecordIdList), projectRecord.project.user.id.eq(userId))

                .fetch();
    }

    @Override
    public Long countAllByProjectAndQuestionExists(Long projectId, Boolean questionExists) {
        return jpaQueryFactory
                .select(projectRecord.id.count())
                .from(projectRecord)
                .join(project).on(projectRecord.project.id.eq(project.id))
                .fetchJoin()
                .where(project.id.eq(projectId), projectQuestionExists(questionExists))
                .groupBy(project.id)
                .fetchOne();
    }

    @Override
    public List<InitialProjectReportResponseDto.SimpleRecordInfo> findThreeRandomSimpleRecordByProjectId(Long projectId) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                InitialProjectReportResponseDto.SimpleRecordInfo.class,
                                projectRecord.createdAt,
                                projectRecord.contents
                        )
                )
                .from(projectRecord)
                .join(project).on(projectRecord.project.id.eq(project.id))
                .fetchJoin()
                .where(project.id.eq(projectId))
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .limit(3)
                .fetch();
    }

    private BooleanExpression projectQuestionExists(Boolean questionExists) {
        return questionExists ? projectRecord.projectQuestion.isNotNull() : null;
    }
}
