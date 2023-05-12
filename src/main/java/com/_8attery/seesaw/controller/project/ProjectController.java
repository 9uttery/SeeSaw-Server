package com._8attery.seesaw.controller.project;

import com._8attery.seesaw.domain.project.Intensity;
import com._8attery.seesaw.domain.user.account.UserAccount;
import com._8attery.seesaw.dto.api.request.ProjectRequestDto;
import com._8attery.seesaw.dto.api.response.ProjectResponseDto;
import com._8attery.seesaw.exception.BaseException;
import com._8attery.seesaw.exception.BaseResponse;
import com._8attery.seesaw.service.project.ProjectService;
import com._8attery.seesaw.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
//@Api(tags = {"4. Project"})
@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final UserService userService;

    private final ProjectService projectService;

    @PostMapping("/api/project")
    public BaseResponse<ProjectResponseDto> addProject(@RequestBody ProjectRequestDto req, @AuthenticationPrincipal UserAccount userAccount) throws BaseException {
        Long userId = userService.resolveUserById(userAccount.getUserId()).getId();

        try {
            ProjectResponseDto res = projectService.addUserProject(userId, req.getValueId(), req.getProjectName(), req.getStartedAt(), req.getEndedAt(), req.getIntensity(), req.getGoal());

            return new BaseResponse<>(res);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
