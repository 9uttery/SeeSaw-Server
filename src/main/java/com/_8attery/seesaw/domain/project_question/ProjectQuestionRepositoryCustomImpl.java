package com._8attery.seesaw.domain.project_question;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com._8attery.seesaw.domain.project_question.QProjectQuestion.projectQuestion;

@RequiredArgsConstructor
@Repository
public class ProjectQuestionRepositoryCustomImpl implements ProjectQuestionRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ProjectQuestion> findAllByQuestionType(QuestionType questionType) {

        return jpaQueryFactory.select(projectQuestion)
                .from(projectQuestion)
                .where(projectQuestion.questionType.eq(questionType))
                .orderBy(projectQuestion.id.asc())
                .fetch();
    }
}
