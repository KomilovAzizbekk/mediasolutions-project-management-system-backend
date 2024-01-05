package uz.prod.backcrm.controller.abs;

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

    String EDIT = "edit/{id}";

    String DELETE = "delete/{id}";

    @GetMapping(GET_BY_ID)
    ApiResult<ProjectDTO> getProjectById(@PathVariable UUID id);

    @GetMapping
    ApiResult<List<ProjectDTO>> getAllProjects(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                               @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @PostMapping
    ApiResult<?> addProject(@Valid @RequestBody ProjectDTO projectDTO);

    @PutMapping(EDIT)
    ApiResult<?> editProject(@PathVariable UUID id, @Valid @RequestBody ProjectDTO projectDTO);

    @DeleteMapping(DELETE)
    ApiResult<?> deleteProject(@PathVariable UUID id);




}
