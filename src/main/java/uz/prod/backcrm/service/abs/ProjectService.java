package uz.prod.backcrm.service.abs;

import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.ProjectDTO;

import java.util.List;
import java.util.UUID;

public interface ProjectService {

    ApiResult<?> addProject(ProjectDTO projectDTO);

    ApiResult<ProjectDTO> getProjectById(UUID id);

    ApiResult<List<ProjectDTO>> getAllProjects(int page, int size);

    ApiResult<List<ProjectDTO>> getMyProjects(int page, int size);

    ApiResult<?> editProject(UUID id, ProjectDTO projectDTO);


    ApiResult<?> deleteProject(UUID id);

}
