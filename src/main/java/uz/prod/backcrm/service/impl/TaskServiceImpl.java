package uz.prod.backcrm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.prod.backcrm.entity.Task;
import uz.prod.backcrm.enums.StatusName;
import uz.prod.backcrm.exceptions.RestException;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.mapper.AttachmentMapper;
import uz.prod.backcrm.mapper.StatusMapper;
import uz.prod.backcrm.mapper.TaskMapper;
import uz.prod.backcrm.mapper.UserMapper;
import uz.prod.backcrm.payload.TaskDTO;
import uz.prod.backcrm.repository.StatusRepository;
import uz.prod.backcrm.repository.TaskRepository;
import uz.prod.backcrm.service.abs.TaskService;
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

    private final AttachmentMapper attachmentMapper;

    @Override
    public ApiResult<TaskDTO> getTaskById(UUID id) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> RestException.restThrow(Message.ID_NOT_FOUND, HttpStatus.BAD_REQUEST));
        return ApiResult.success(taskMapper.toDTO(task));
    }

    @Override
    public ApiResult<List<TaskDTO>> getAllTasksByProjectId(UUID id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Task> taskList = taskRepository.getAllByProjectId(id, pageable);
        return ApiResult.success(taskMapper.toDTOList(taskList));
    }

    @Override
    public ApiResult<?> addTask(TaskDTO taskDTO) {
//        Task task = Task.builder()
//                .name(taskDTO.getName())
//                .deadline(taskDTO.getDeadline())
//                .status(statusMapper.toEntity(taskDTO.getStatusDTO()))
//                .responsibleUsers(userMapper.toEntityList(taskDTO.getResponsibleUsers()))
//                .attachmentList(attachmentMapper.toEntityList(taskDTO.getAttachmentDTOList()))
//                .build();
        Task task = taskMapper.toEntity(taskDTO);
        taskRepository.save(task);
        return ApiResult.success(Message.SAVED_SUCCESSFULLY);
    }

    @Override
    public ApiResult<?> editTask(UUID id, TaskDTO taskDTO) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> RestException.restThrow(Message.ID_NOT_FOUND, HttpStatus.BAD_REQUEST));
        task.setName(taskDTO.getName());
        task.setDeadline(taskDTO.getDeadline());
        task.setStatus(statusMapper.toEntity(taskDTO.getStatusDTO()));
        task.setResponsibleUsers(userMapper.toEntityList(taskDTO.getResponsibleUsers()));
        task.setAttachmentList(attachmentMapper.toEntityList(taskDTO.getAttachmentDTOList()));
        taskRepository.save(task);
        return ApiResult.success(Message.UPDATED_SUCCESSFULLY);
    }

    @Override
    public ApiResult<?> deleteTask(UUID id) {
        try {
            taskRepository.deleteById(id);
            return ApiResult.success(Message.DELETED_SUCCESSFULLY);
        } catch (Exception e){
            throw RestException.restThrow(Message.DELETE_FAILED, HttpStatus.CONFLICT);
        }

    }
}
