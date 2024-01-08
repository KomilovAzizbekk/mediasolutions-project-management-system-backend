package uz.prod.backcrm.controller.abs;

import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.prod.backcrm.entity.Task;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.TaskDTO;
import uz.prod.backcrm.utills.constants.Rest;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping(TaskController.TASK)
public interface TaskController {

    String TASK = Rest.BASE_PATH_V1 + "task/";

    String GET_BY_ID = "{id}";

    String GET_BY_PROJECT_ID = "by-project/{id}";

    String GET_MY_TASKS = "my-tasks";

    String ADD = "add";

    String EDIT = "edit/{id}";

    String DELETE = "delete/{id}";

    @GetMapping(GET_BY_ID)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PM', 'ROLE_PROGRAMMER')")
    ApiResult<TaskDTO> getTaskById(@PathVariable UUID id);

    @GetMapping(GET_BY_PROJECT_ID)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PM')")
    ApiResult<List<TaskDTO>> getAllTasksByProjectId(@PathVariable UUID id,
                                                    @RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                                    @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @GetMapping(GET_MY_TASKS)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PM', 'ROLE_PROGRAMMER')")
    ApiResult<List<TaskDTO>> getAllMyTasks(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                           @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @PostMapping(ADD)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PM')")
    ApiResult<?> addTask(@Valid @RequestBody TaskDTO taskDTO);

    @PutMapping(EDIT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PM')")
    ApiResult<?> editTask(@PathVariable UUID id, @Valid @RequestBody TaskDTO taskDTO);

    @DeleteMapping(DELETE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PM')")
    ApiResult<?> deleteTask(@PathVariable UUID id);


}
