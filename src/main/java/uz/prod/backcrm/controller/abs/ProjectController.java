package uz.prod.backcrm.controller.abs;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.ProjectDTO;
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

    String EDIT = "edit/{id}";

    String DELETE = "delete/{id}";

    @GetMapping(GET_BY_ID)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PM', 'ROLE_PROGRAMMER')")
    ApiResult<ProjectDTO> getProjectById(@PathVariable UUID id);

    @GetMapping(GET_ALL)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ApiResult<List<ProjectDTO>> getAllProjects(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                               @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @GetMapping(GET_MY_PROJECTS)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PM', 'ROLE_PROGRAMMER')")
    ApiResult<List<ProjectDTO>> getMyProjects(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                               @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @PostMapping(ADD)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ApiResult<?> addProject(@Valid @RequestBody ProjectDTO projectDTO);

    @PutMapping(EDIT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ApiResult<?> editProject(@PathVariable UUID id, @Valid @RequestBody ProjectDTO projectDTO);

    @DeleteMapping(DELETE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ApiResult<?> deleteProject(@PathVariable UUID id);




}
