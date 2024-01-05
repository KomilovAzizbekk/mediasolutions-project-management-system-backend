package uz.prod.backcrm.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.prod.backcrm.controller.abs.TaskController;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.TaskDTO;
import uz.prod.backcrm.service.abs.TaskService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TaskControllerImpl implements TaskController {

    private final TaskService taskService;

    @Override
    public ApiResult<TaskDTO> getTaskById(UUID id) {
        return taskService.getTaskById(id);
    }

    @Override
    public ApiResult<List<TaskDTO>> getAllTasksByProjectId(UUID id, int page, int size) {
        return taskService.getAllTasksByProjectId(id, page, size);
    }

    @Override
    public ApiResult<?> addTask(TaskDTO taskDTO) {
        return taskService.addTask(taskDTO);
    }

    @Override
    public ApiResult<?> editTask(UUID id, TaskDTO taskDTO) {
        return taskService.editTask(id, taskDTO);
    }

    @Override
    public ApiResult<?> deleteTask(UUID id) {
        return taskService.deleteTask(id);
    }
}
