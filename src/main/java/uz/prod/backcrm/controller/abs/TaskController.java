package uz.prod.backcrm.controller.abs;

import io.swagger.annotations.Api;
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

    String GET_P_ID = "by-project/{id}";

    String EDIT = "edit/{id}";

    String DELETE = "delete/{id}";

    @GetMapping(GET_BY_ID)
    ApiResult<TaskDTO> getTaskById(@PathVariable UUID id);

    @GetMapping(GET_P_ID)
    ApiResult<List<TaskDTO>> getAllTasksByProjectId(@PathVariable UUID id,
                                                    @RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                                    @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @PostMapping
    ApiResult<?> addTask(@Valid @RequestBody TaskDTO taskDTO);

    @PutMapping(EDIT)
    ApiResult<?> editTask(@PathVariable UUID id, @Valid @RequestBody TaskDTO taskDTO);

    @DeleteMapping(DELETE)
    ApiResult<?> deleteTask(@PathVariable UUID id);


}
