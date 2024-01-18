package uz.prod.backcrm.service.abs;

import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.ProjectResDTO;

import java.util.List;
import java.util.UUID;

public interface ProjectService {

    ApiResult<?> addProject(ProjectResDTO projectDTO);

    ApiResult<ProjectResDTO> getProjectById(UUID id);

    ApiResult<List<ProjectResDTO>> getAllProjects(int page, int size);

    ApiResult<List<ProjectResDTO>> getMyProjects(int page, int size);

    ApiResult<?> editProject(UUID id, ProjectResDTO projectDTO);


    ApiResult<?> deleteProject(UUID id);

    ApiResult<?> addUserToProject(List<UUID> userIdList, UUID pId);
}
