package com._8attery.seesaw.service.util;

import com._8attery.seesaw.domain.project.Project;
import com._8attery.seesaw.domain.project.ProjectRepository;
import com._8attery.seesaw.domain.project_question.ProjectQuestion;
import com._8attery.seesaw.domain.project_question.ProjectQuestionRepository;
import com._8attery.seesaw.domain.project_remembrance.ProjectRemembrance;
import com._8attery.seesaw.domain.project_remembrance.ProjectRemembranceRepository;
import com._8attery.seesaw.domain.user.User;
import com._8attery.seesaw.domain.user.UserRepository;
import com._8attery.seesaw.domain.value.Value;
import com._8attery.seesaw.domain.value.ValueRepository;
import com._8attery.seesaw.exception.custom.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ServiceUtils {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ValueRepository valueRepository;
    private final ProjectQuestionRepository projectQuestionRepository;
    private final ProjectRemembranceRepository projectRemembranceRepository;

    public boolean validateUser(Long userId) {
        return userRepository.existsById(userId);
    }

    public boolean validateProject(Long projectId) {
        return projectRepository.existsById(projectId);
    }

    public boolean validateValue(Long valueId) {
        return valueRepository.existsById(valueId);
    }

    public User retrieveUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("userId로 user를 찾을 수 없습니다.")
        );
    }

    public Project retrieveProjectById(Long projectId) {
        return projectRepository.findById(projectId).orElseThrow(
                () -> new ResourceNotFoundException("projectId로 project를 찾을 수 없습니다.")
        );
    }

    public Value retrieveValueById(Long valueId) {
        return valueRepository.findById(valueId).orElseThrow(
                () -> new ResourceNotFoundException("valueId로 value를 찾을 수 없습니다.")
        );
    }

    public ProjectQuestion retrieveProjectQuestionById(Long projectQuestionId) {
        return projectQuestionRepository.findById(projectQuestionId).orElseThrow(
                () -> new ResourceNotFoundException("projectQuestionId로 projectQuestion을 찾을 수 없습니다.")
        );
    }

    public ProjectRemembrance retrieveProjectRemembranceById(Long projectRemembranceId) {
        return projectRemembranceRepository.findById(projectRemembranceId).orElseThrow(
                () -> new ResourceNotFoundException("projectRemembranceId로 projectRemembrance을 찾을 수 없습니다.")
        );
    }
}
