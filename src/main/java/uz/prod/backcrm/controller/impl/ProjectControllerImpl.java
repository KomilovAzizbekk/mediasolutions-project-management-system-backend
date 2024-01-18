package uz.prod.backcrm.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.prod.backcrm.controller.abs.ProjectController;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.ProjectResDTO;
import uz.prod.backcrm.service.abs.ProjectService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProjectControllerImpl implements ProjectController {

    private final ProjectService projectService;

    @Override
    public ApiResult<ProjectResDTO> getProjectById(UUID id) {
        return projectService.getProjectById(id);
    }

    @Override
    public ApiResult<List<ProjectResDTO>> getAllProjects(int page, int size) {
        return projectService.getAllProjects(page, size);
    }

    @Override
    public ApiResult<List<ProjectResDTO>> getMyProjects(int page, int size) {
        return projectService.getMyProjects(page, size);
    }

    @Override
    public ApiResult<?> addProject(ProjectResDTO projectDTO) {
        return projectService.addProject(projectDTO);
    }

    @Override
    public ApiResult<?> addUserToProject(List<UUID> userIdList, UUID pId) {
        return projectService.addUserToProject(userIdList, pId);
    }

    @Override
    public ApiResult<?> editProject(UUID id, ProjectResDTO projectDTO) {
        return projectService.editProject(id, projectDTO);
    }

    @Override
    public ApiResult<?> deleteProject(UUID id) {
        return projectService.deleteProject(id);
    }
}
