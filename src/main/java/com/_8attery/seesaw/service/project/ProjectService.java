package com._8attery.seesaw.service.project;

import com._8attery.seesaw.domain.project.ProjectRepository;
import com._8attery.seesaw.dto.api.response.ProjectCardResponseDto;
import com._8attery.seesaw.dto.api.response.ProjectResponseDto;
import com._8attery.seesaw.exception.BaseException;
import com._8attery.seesaw.exception.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Transactional
    public ProjectResponseDto addUserProject(Long userId, Long valueId, String projectName, LocalDateTime startedAt, LocalDateTime endedAt, String intensity, String goal) throws BaseException {

        try {
            projectRepository.addIngProject(userId, valueId, projectName, startedAt, endedAt, intensity, goal);

            Optional<ProjectResponseDto> projectResponseDto = projectRepository.getProject(userId, valueId, projectName);
            return projectResponseDto.orElse(new ProjectResponseDto(0L, null, null, null, null, null, false));

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public List<ProjectCardResponseDto> getProgressProjectList(Long userId) throws BaseException {
        try {
            List<ProjectCardResponseDto> list = projectRepository.findProgressProjectList(userId);
            return list;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public List<ProjectCardResponseDto> getCompleteProjectList(Long userId) throws BaseException {
        try {
            List<ProjectCardResponseDto> list = projectRepository.findCompleteProjectList(userId);
            return list;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
