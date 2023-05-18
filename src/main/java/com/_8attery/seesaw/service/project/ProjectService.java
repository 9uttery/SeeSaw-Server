package com._8attery.seesaw.service.project;

import com._8attery.seesaw.domain.project.Project;
import com._8attery.seesaw.domain.project.ProjectRepository;
import com._8attery.seesaw.domain.project.ProjectRepositoryCustom;
import com._8attery.seesaw.domain.project_emotion.ProjectEmotionRepository;
import com._8attery.seesaw.domain.project_emotion.ProjectEmotionRepositoryCustom;
import com._8attery.seesaw.domain.project_record.ProjectRecord;
import com._8attery.seesaw.domain.project_record.ProjectRecordRepository;
import com._8attery.seesaw.dto.api.request.ProjectEmotionRequestDto;
import com._8attery.seesaw.dto.api.request.ProjectRecordRequestDto;
import com._8attery.seesaw.dto.api.response.*;
import com._8attery.seesaw.exception.BaseException;
import com._8attery.seesaw.service.util.ServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static com._8attery.seesaw.exception.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectRepositoryCustom projectRepositoryCustom;
    private final ProjectEmotionRepository projectEmotionRepository;
    private final ProjectEmotionRepositoryCustom projectEmotionRepositoryCustom;
    private final ProjectRecordRepository projectRecordRepository;
    private final ServiceUtils serviceUtils;

    @Transactional
    public ProjectResponseDto addUserProject(Long userId, Long valueId, String projectName, LocalDateTime startedAt, LocalDateTime endedAt, String intensity, String goal) throws BaseException {

        try {
            projectRepository.addIngProject(userId, valueId, projectName, startedAt, endedAt, intensity, goal);

            Optional<ProjectResponseDto> projectResponseDto = projectRepository.getProject(userId, valueId, projectName);
            return projectResponseDto.orElse(new ProjectResponseDto(0L, null, null, null, null, null, false));

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public ProjectResponseDto updateUserProject(Long projectId, Long userId, Long valueId, String projectName, LocalDateTime startedAt, LocalDateTime endedAt, String intensity, String goal) throws BaseException {
        try {
            projectRepository.updateIngProject(projectId, valueId, projectName, startedAt, endedAt, intensity, goal);

            Optional<ProjectResponseDto> projectResponseDto = projectRepository.getProject(userId, valueId, projectName);
            return projectResponseDto.orElse(new ProjectResponseDto(0L, null, null, null, null, null, false));

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public Long retrieveUserId(Long projectId) throws BaseException{
        try{
            Long userId = projectRepository.getUserId(projectId);
            if (userId == null)
                throw new BaseException(POSTS_EMPTY_POST_ID);
            return userId;
        } catch (Exception exception){
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public void deleteUserProject(Long projectId) throws BaseException {
        try{
            int result = projectRepository.deleteProjectByProjectId(projectId);
            if (result == 0)
                throw new BaseException(DELETE_FAIL_POST);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<ProjectCardResponseDto> getProgressProjectList(Long userId) throws BaseException {
        try {
            List<ProjectCardResponseDto> list = projectRepository.findProgressProjectList(userId);
            return list;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<ProjectCardResponseDto> getCompleteProjectList(Long userId) throws BaseException {
        try {
            List<ProjectCardResponseDto> list = projectRepository.findCompleteProjectList(userId);
            return list;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public ProjectDetailsResponseDto getProjectDetails(Long userId, Long projectId) {
        serviceUtils.retrieveUserById(userId);
        serviceUtils.retrieveProjectById(projectId);
        ProjectDetailsResponseDto result = projectRepositoryCustom.getProjectDetails(projectId);

        if (!result.getIsFinished()) {
            LocalDateTime middleDate = result.getStartedAt().plusSeconds(ChronoUnit.SECONDS.between(result.getStartedAt(), result.getEndedAt()) / 2).truncatedTo(ChronoUnit.DAYS);
            result.setIsHalfProgressed(LocalDateTime.now().toLocalDate().isAfter(middleDate.toLocalDate()));
        }

        return result;
    }

    public ProjectEmotionResponseDto addEmotionToProject(Long userId, ProjectEmotionRequestDto projectEmotionRequestDto) {
        serviceUtils.retrieveUserById(userId);
        serviceUtils.retrieveProjectById(projectEmotionRequestDto.getProjectId());

        return projectEmotionRepositoryCustom.updateProjectEmotion(projectEmotionRequestDto);
    }

    public ProjectRecordResponseDto addRecordToProject(Long userId, ProjectRecordRequestDto projectRecordRequestDto) {
        serviceUtils.retrieveUserById(userId);
        Project retrievedProject = serviceUtils.retrieveProjectById(projectRecordRequestDto.getProjectId());

        ProjectRecord projectRecord = projectRecordRepository.save(
                ProjectRecord
                        .projectRecordBuilder()
                        .project(retrievedProject)
                        .question(projectRecordRequestDto.getQuestion())
                        .contents(projectRecordRequestDto.getContents())
                        .temp(projectRecordRequestDto.getTemp())
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        return ProjectRecordResponseDto.from(projectRecordRepository.save(projectRecord));
    }
}
