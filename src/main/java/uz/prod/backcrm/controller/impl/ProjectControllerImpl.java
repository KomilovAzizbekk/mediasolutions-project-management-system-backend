package uz.prod.backcrm.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.prod.backcrm.controller.abs.ProjectController;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.ProjectDTO;
import uz.prod.backcrm.service.abs.ProjectService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProjectControllerImpl implements ProjectController {

    private final ProjectService projectService;

    @Override
    public ApiResult<ProjectDTO> getProjectById(UUID id) {
        return projectService.getProjectById(id);
    }

    @Override
    public ApiResult<List<ProjectDTO>> getAllProjects(int page, int size) {
        return projectService.getAllProjects(page, size);
    }

    @Override
    public ApiResult<?> addProject(ProjectDTO projectDTO) {
        return projectService.addProject(projectDTO);
    }

    @Override
    public ApiResult<?> editProject(UUID id, ProjectDTO projectDTO) {
        return projectService.editProject(id, projectDTO);
    }

    @Override
    public ApiResult<?> deleteProject(UUID id) {
        return projectService.deleteProject(id);
    }
}
