package uz.prod.backcrm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.prod.backcrm.entity.Task;
import uz.prod.backcrm.entity.User;
import uz.prod.backcrm.exceptions.RestException;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.mapper.FileMapper;
import uz.prod.backcrm.mapper.StatusMapper;
import uz.prod.backcrm.mapper.TaskMapper;
import uz.prod.backcrm.mapper.UserMapper;
import uz.prod.backcrm.payload.TaskDTO;
import uz.prod.backcrm.repository.TaskRepository;
import uz.prod.backcrm.service.abs.TaskService;
import uz.prod.backcrm.utills.CommonUtils;
import uz.prod.backcrm.utills.constants.Message;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    private final StatusMapper statusMapper;

    private final UserMapper userMapper;

    private final FileMapper fileMapper;

    private final MessageSource messageSource;

    @Override
    public ApiResult<TaskDTO> getTaskById(UUID id) {
        String message = CommonUtils.createMessage(Message.NOT_FOUND, messageSource, new Object[]{id});
        Task task = taskRepository.findById(id).orElseThrow(
                () -> RestException.restThrow(message, HttpStatus.BAD_REQUEST));
        return ApiResult.success(taskMapper.toDTO(task));
    }

    @Override
    public ApiResult<List<TaskDTO>> getAllTasksByProjectId(UUID id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> taskList = taskRepository.getAllByProjectId(id, pageable);
        return ApiResult.success(taskMapper.toDTOList(taskList));
    }

    @Override
    public ApiResult<List<TaskDTO>> getAllMyTasks(int page, int size) {
        User user = CommonUtils.getUserFromRequest();
        Pageable pageable = PageRequest.of(page, size);
        return ApiResult.success(taskMapper.toDTOList(taskRepository.findAllByUsers(user, pageable)));
    }

    @Override
    public ApiResult<?> addTask(TaskDTO taskDTO) {
        Task task = taskMapper.toEntity(taskDTO);
        taskRepository.save(task);
        return ApiResult.success(CommonUtils.createMessage(Message.ADDED_SUCCESSFULLY, messageSource, new Object[]{taskDTO}));
    }

    @Override
    public ApiResult<?> editTask(UUID id, TaskDTO taskDTO) {
        String message = CommonUtils.createMessage(Message.NOT_FOUND, messageSource, new Object[]{id});
        Task task = taskRepository.findById(id).orElseThrow(
                () -> RestException.restThrow(message, HttpStatus.BAD_REQUEST));
        task.setName(taskDTO.getName());
        task.setDeadline(taskDTO.getDeadline());
        task.setStatus(statusMapper.toEntity(taskDTO.getStatusDTO()));
        task.setUsers(userMapper.toEntityList(taskDTO.getUserDTOS()));
        task.setFiles(fileMapper.toEntityList(taskDTO.getFileDTOS()));
        taskRepository.save(task);
        return ApiResult.success(CommonUtils.createMessage(Message.UPDATED_SUCCESSFULLY, messageSource, new Object[]{taskDTO}));
    }

    @Override
    public ApiResult<?> deleteTask(UUID id) {
        try {
            taskRepository.deleteById(id);
            return ApiResult.success(CommonUtils.createMessage(Message.DELETED_SUCCESSFULLY, messageSource, new Object[]{id}));
        } catch (Exception e){
            throw RestException.restThrow(CommonUtils.createMessage(Message.DELETE_FAILED, messageSource, new Object[]{id}), HttpStatus.CONFLICT);
        }

    }
}
