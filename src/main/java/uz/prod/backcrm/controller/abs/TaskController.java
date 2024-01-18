package uz.prod.backcrm.controller.abs;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.TaskResDTO;
import uz.prod.backcrm.payload.TaskReqDTO;
import uz.prod.backcrm.utills.constants.Rest;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping(TaskController.TASK)
public interface TaskController {

    String TASK = Rest.BASE_PATH_V1 + "task/";

    String GET_BY_ID = "{id}";

    String GET_BY_PROJECT_ID = "by-project/{id}";

    String GET_PENDING = "get-pending";

    String GET_MY_TASKS = "my-tasks";

    String ADD = "add/{pId}";
    String ACCEPT_OR_DECLINE = "a-d/{id}";
    String UPDATE_TASK_STATUS = "update-ts/{tId}";

    String EDIT = "edit/{id}";

    String DELETE = "delete/{id}";

    @GetMapping(GET_BY_ID)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PM', 'ROLE_PROGRAMMER')")
    ApiResult<TaskResDTO> getTaskById(@PathVariable UUID id);

    @GetMapping(GET_PENDING)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PM')")
    ApiResult<List<TaskResDTO>> getPending(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                           @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @GetMapping(GET_BY_PROJECT_ID)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PM')")
    ApiResult<List<TaskResDTO>> getAllTasksByProjectId(@PathVariable UUID id,
                                                       @RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @GetMapping(GET_MY_TASKS)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PM', 'ROLE_PROGRAMMER')")
    ApiResult<List<TaskResDTO>> getAllMyTasks(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                              @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @PostMapping(ADD)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PM')")
    ApiResult<?> addTask(@PathVariable UUID pId, @Valid @RequestBody List<TaskReqDTO> taskReqDTOS);

    @PostMapping(ACCEPT_OR_DECLINE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PM')")
    ApiResult<?> acceptOrDecline(@RequestParam(defaultValue = "false") boolean accept,
                                 @PathVariable UUID id);

    @PostMapping(UPDATE_TASK_STATUS)
    @PreAuthorize("hasAnyRole('ROLE_PM', 'ROLE_PROGRAMMER', 'ROLE_ADMIN')")
    ApiResult<?> updateTaskStatus(@PathVariable UUID tId);


    @PutMapping(EDIT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PM')")
    ApiResult<?> editTask(@PathVariable UUID id, @Valid @RequestBody TaskReqDTO reqDTO);

    @DeleteMapping(DELETE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PM')")
    ApiResult<?> deleteTask(@PathVariable UUID id);


}
