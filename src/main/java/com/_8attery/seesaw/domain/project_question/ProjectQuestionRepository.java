package com._8attery.seesaw.domain.project_question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectQuestionRepository extends JpaRepository<ProjectQuestion, Long> {
    @Query(value = "SELECT * FROM ss_project_question WHERE question_type = 'REGULAR' ORDER BY RAND() LIMIT 1", nativeQuery = true)
    ProjectQuestion findRandomRegularQuestion();
}
