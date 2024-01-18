package uz.prod.backcrm.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.prod.backcrm.controller.abs.TaskController;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.TaskResDTO;
import uz.prod.backcrm.payload.TaskReqDTO;
import uz.prod.backcrm.service.abs.TaskService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TaskControllerImpl implements TaskController {

    private final TaskService taskService;

    @Override
    public ApiResult<TaskResDTO> getTaskById(UUID id) {
        return taskService.getTaskById(id);
    }

    @Override
    public ApiResult<List<TaskResDTO>> getPending(int page, int size) {
        return taskService.getPending(page, size);
    }

    @Override
    public ApiResult<List<TaskResDTO>> getAllTasksByProjectId(UUID id, int page, int size) {
        return taskService.getAllTasksByProjectId(id, page, size);
    }

    @Override
    public ApiResult<List<TaskResDTO>> getAllMyTasks(int page, int size) {
        return taskService.getAllMyTasks(page, size);
    }

    @Override
    public ApiResult<?> addTask(UUID pId, List<TaskReqDTO> taskReqDTOS) {
        return taskService.addTaskToProject(pId, taskReqDTOS);
    }

    @Override
    public ApiResult<?> acceptOrDecline(boolean accept, UUID id) {
        return taskService.acceptOrDecline(accept,id);
    }

    @Override
    public ApiResult<?> updateTaskStatus(UUID tId) {
        return taskService.updateTaskStatus(tId);
    }

    @Override
    public ApiResult<?> editTask(UUID id, TaskReqDTO reqDTO) {
        return taskService.editTask(id, reqDTO);
    }

    @Override
    public ApiResult<?> deleteTask(UUID id) {
        return taskService.deleteTask(id);
    }
}
