package com._8attery.seesaw.domain.project_record;

import com._8attery.seesaw.dto.api.response.ProjectRecordResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
                .fetch();
    }
}
