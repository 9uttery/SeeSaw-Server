package com._8attery.seesaw.domain.project_question;

import java.util.List;

public interface ProjectQuestionRepositoryCustom {
    List<ProjectQuestion> findAllByQuestionType(QuestionType questionType);
}
