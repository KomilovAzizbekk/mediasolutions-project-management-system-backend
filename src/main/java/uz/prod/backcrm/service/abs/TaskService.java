package uz.prod.backcrm.service.abs;

import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.TaskDTO;

import java.util.List;
import java.util.UUID;

public interface TaskService {

    ApiResult<TaskDTO> getTaskById(UUID id);

    ApiResult<List<TaskDTO>> getAllTasksByProjectId(UUID id, int page, int size);

    ApiResult<?> addTask(TaskDTO taskDTO);


    ApiResult<?> editTask(UUID id, TaskDTO taskDTO);


    ApiResult<?> deleteTask(UUID id);


}
