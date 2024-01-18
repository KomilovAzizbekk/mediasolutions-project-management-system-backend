package uz.prod.backcrm.service.abs;

import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.TaskResDTO;
import uz.prod.backcrm.payload.TaskReqDTO;

import java.util.List;
import java.util.UUID;

public interface TaskService {

    ApiResult<TaskResDTO> getTaskById(UUID id);

    ApiResult<List<TaskResDTO>> getAllTasksByProjectId(UUID id, int page, int size);

    ApiResult<List<TaskResDTO>> getAllMyTasks(int page, int size);

    ApiResult<?> addTaskToProject(UUID pId, List<TaskReqDTO> taskReqDTOS);


    ApiResult<?> editTask(UUID id, TaskReqDTO reqDTO);


    ApiResult<?> deleteTask(UUID id);

    ApiResult<?> updateTaskStatus(UUID tId);

    ApiResult<List<TaskResDTO>> getPending(int page, int size);

    ApiResult<?> acceptOrDecline(boolean accept, UUID id);
}
