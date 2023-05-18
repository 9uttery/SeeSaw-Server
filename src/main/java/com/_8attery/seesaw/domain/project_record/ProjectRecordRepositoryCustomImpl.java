package com._8attery.seesaw.domain.project_record;

import com._8attery.seesaw.dto.api.response.ProjectRecordResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com._8attery.seesaw.domain.project_record.QProjectRecord.projectRecord;

@RequiredArgsConstructor
@Repository
public class ProjectRecordRepositoryCustomImpl implements ProjectRecordRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<ProjectRecordResponseDto> findAllRecordsByProjectId(Long projectId) {

        return jpaQueryFactory
                .select(projectRecord)
                .from(projectRecord)
                .where(projectRecord.project.id.eq(projectId))
                .fetch()
                .stream()
                .map(ProjectRecordResponseDto::from)
                .collect(Collectors.toList());
    }
}
