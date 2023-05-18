package com._8attery.seesaw.domain.project_emotion;

import com._8attery.seesaw.dto.api.request.ProjectEmotionRequestDto;
import com._8attery.seesaw.dto.api.response.ProjectEmotionResponseDto;
import com.querydsl.core.dml.UpdateClause;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

import static com._8attery.seesaw.domain.project_emotion.QProjectEmotion.projectEmotion;

@RequiredArgsConstructor
@Repository
public class ProjectEmotionRepositoryCustomImpl implements ProjectEmotionRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Transactional
    @Override
    public ProjectEmotionResponseDto updateProjectEmotion(ProjectEmotionRequestDto requestDto) {

        UpdateClause<JPAUpdateClause> updateClause = jpaQueryFactory.update(projectEmotion);

        switch (requestDto.getEmotion()) {
            case LIKE:
                updateClause.set(projectEmotion.likeCnt, projectEmotion.likeCnt.add(1));
                break;
            case NICE:
                updateClause.set(projectEmotion.niceCnt, projectEmotion.niceCnt.add(1));
                break;
            case IDK:
                updateClause.set(projectEmotion.idkCnt, projectEmotion.idkCnt.add(1));
                break;
            case ANGRY:
                updateClause.set(projectEmotion.angryCnt, projectEmotion.angryCnt.add(1));
                break;
            case SAD:
                updateClause.set(projectEmotion.sadCnt, projectEmotion.sadCnt.add(1));
                break;
        }

        updateClause.where(projectEmotion.project.id.eq(requestDto.getProjectId())).execute();

        return jpaQueryFactory.select(Projections.constructor(ProjectEmotionResponseDto.class, projectEmotion.likeCnt, projectEmotion.niceCnt, projectEmotion.idkCnt, projectEmotion.angryCnt, projectEmotion.sadCnt))
                .from(projectEmotion)
                .where(projectEmotion.project.id.eq(requestDto.getProjectId()))
                .fetchOne();

    }
}
