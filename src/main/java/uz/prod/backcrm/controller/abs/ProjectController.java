package uz.prod.backcrm.controller.abs;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.ProjectResDTO;
import uz.prod.backcrm.utills.constants.Rest;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping(ProjectController.PROJECT)
public interface ProjectController {

    String PROJECT = Rest.BASE_PATH_V1 + "project/";

    String GET_BY_ID = "{id}";

    String GET_ALL = "all";

    String GET_MY_PROJECTS = "my-projects";

    String ADD = "add";
    String ADD_USER_TO_PROJECT = "add-user-to-project/{pId}";
    String EDIT = "edit/{id}";

    String DELETE = "delete/{id}";

    @GetMapping(GET_BY_ID)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PM', 'ROLE_PROGRAMMER')")
    ApiResult<ProjectResDTO> getProjectById(@PathVariable UUID id);

    @GetMapping(GET_ALL)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ApiResult<List<ProjectResDTO>> getAllProjects(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                                  @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @GetMapping(GET_MY_PROJECTS)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PM', 'ROLE_PROGRAMMER')")
    ApiResult<List<ProjectResDTO>> getMyProjects(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                                 @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @PostMapping(ADD)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PM')")
    ApiResult<?> addProject(@Valid @RequestBody ProjectResDTO projectDTO);

    @PostMapping(ADD_USER_TO_PROJECT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ApiResult<?> addUserToProject(@RequestBody List<UUID> userIdList, @PathVariable UUID pId);

    @PutMapping(EDIT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ApiResult<?> editProject(@PathVariable UUID id, @Valid @RequestBody ProjectResDTO projectDTO);

    @DeleteMapping(DELETE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ApiResult<?> deleteProject(@PathVariable UUID id);




}
