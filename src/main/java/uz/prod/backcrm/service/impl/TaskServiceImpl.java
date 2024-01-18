package uz.prod.backcrm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.prod.backcrm.entity.Project;
import uz.prod.backcrm.entity.Task;
import uz.prod.backcrm.entity.User;
import uz.prod.backcrm.enums.StatusName;
import uz.prod.backcrm.exceptions.RestException;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.mapper.AttachmentMapper;
import uz.prod.backcrm.mapper.StatusMapper;
import uz.prod.backcrm.mapper.TaskMapper;
import uz.prod.backcrm.payload.TaskResDTO;
import uz.prod.backcrm.payload.TaskReqDTO;
import uz.prod.backcrm.repository.ProjectRepository;
import uz.prod.backcrm.repository.StatusRepository;
import uz.prod.backcrm.repository.TaskRepository;
import uz.prod.backcrm.repository.UserRepository;
import uz.prod.backcrm.service.abs.TaskService;
import uz.prod.backcrm.utills.CommonUtils;
import uz.prod.backcrm.utills.constants.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    private final StatusMapper statusMapper;

    private final MessageSource messageSource;

    private final ProjectRepository projectRepository;

    private final AttachmentMapper attachmentMapper;

    private final StatusRepository statusRepository;

    private final UserRepository userRepository;

    @Override
    public ApiResult<TaskResDTO> getTaskById(UUID id) {
        String message = CommonUtils.createMessage(Message.NOT_FOUND, messageSource, new Object[]{id});
        Task task = taskRepository.findById(id).orElseThrow(
                () -> RestException.restThrow(message, HttpStatus.BAD_REQUEST));
        return ApiResult.success(taskMapper.toDTO(task));
    }

    @Override
    public ApiResult<List<TaskResDTO>> getAllTasksByProjectId(UUID id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> taskList = taskRepository.getAllByProjectId(id, pageable);
        return ApiResult.success(taskMapper.toDTOList(taskList));
    }

    @Override
    public ApiResult<List<TaskResDTO>> getAllMyTasks(int page, int size) {
        User user = CommonUtils.getUserFromSecurityContext();
        Pageable pageable = PageRequest.of(page, size);
        return ApiResult.success(taskMapper.toDTOList(taskRepository.findAllByUsers(user, pageable)));
    }

    @Override
    public ApiResult<?> addTaskToProject(UUID pId, List<TaskReqDTO> taskReqDTOS) {
        String message = CommonUtils.createMessage(Message.NOT_FOUND, messageSource, new Object[]{pId});
        Project project = projectRepository.findById(pId).orElseThrow(
                () -> RestException.restThrow(message, HttpStatus.BAD_REQUEST));
        List<Task> tasks = toEntityListFromReq(taskReqDTOS);
        for (Task task : tasks) {
            task.setStatus(statusRepository.findByName(StatusName.CREATED));
        }
        List<Task> taskList = taskRepository.saveAll(tasks);
        project.setTasks(taskList);
        projectRepository.save(project);
        return ApiResult.success(CommonUtils.createMessage(Message.ADDED_SUCCESSFULLY, messageSource, null));
    }

    @Override
    public ApiResult<?> editTask(UUID id, TaskReqDTO reqDTO) {
        String message = CommonUtils.createMessage(Message.NOT_FOUND, messageSource, new Object[]{id});
        Task task = taskRepository.findById(id).orElseThrow(
                () -> RestException.restThrow(message, HttpStatus.BAD_REQUEST));
        task.setName(reqDTO.getName());
        task.setDeadline(reqDTO.getDeadline());
        task.setAttachments(attachmentMapper.toEntityList(reqDTO.getAttachments()));
        task.setStatus(statusMapper.toEntity(reqDTO.getStatus()));
        task.setUsers(userRepository.findAllById(reqDTO.getUserIds()));
        taskRepository.save(task);
        return ApiResult.success(CommonUtils.createMessage(Message.UPDATED_SUCCESSFULLY, messageSource, null));
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

    @Override
    public ApiResult<?> updateTaskStatus(UUID tId) {
        String message = CommonUtils.createMessage(Message.NOT_FOUND, messageSource, new Object[]{tId});
        Task task = taskRepository.findById(tId).orElseThrow(
                () -> RestException.restThrow(message, HttpStatus.BAD_REQUEST));
        task.setStatus(statusRepository.findByName(StatusName.PENDING));
        taskRepository.save(task);
        return ApiResult.success(CommonUtils.createMessage(Message.UPDATED_SUCCESSFULLY, messageSource, null));
    }

    @Override
    public ApiResult<List<TaskResDTO>> getPending(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> status = taskRepository.findAllByStatus(pageable, StatusName.PENDING);
        return ApiResult.success(taskMapper.toDTOList(status));
    }

    @Override
    public ApiResult<?> acceptOrDecline(boolean accept, UUID id) {
        String message = CommonUtils.createMessage(Message.NOT_FOUND, messageSource, new Object[]{id});
        Task task = taskRepository.findById(id).orElseThrow(
                () -> RestException.restThrow(message, HttpStatus.BAD_REQUEST));
        if (accept) {
            task.setStatus(statusRepository.findByName(StatusName.COMPLETED));
            taskRepository.save(task);
            return ApiResult.success(CommonUtils.createMessage(Message.ACCEPTED, messageSource, null));
        } else {
            task.setStatus(statusRepository.findByName(StatusName.IN_PROGRESS));
            taskRepository.save(task);
            return ApiResult.success(CommonUtils.createMessage(Message.REJECTED, messageSource, null));
        }
    }

    private Task toEntityFromReq(TaskReqDTO reqDTO){
        if (reqDTO == null) {
            return null;
        }

        List<User> userList = userRepository.findAllById(reqDTO.getUserIds());

        return Task.builder()
                .name(reqDTO.getName())
                .deadline(reqDTO.getDeadline())
                .attachments(attachmentMapper.toEntityList(reqDTO.getAttachments()))
                .users(userList)
                .build();
    }

    private List<Task> toEntityListFromReq(List<TaskReqDTO> reqDTOS){
        if (reqDTOS == null) {
            return null;
        }
        List<Task> tasks = new ArrayList<>(reqDTOS.size());
        for (TaskReqDTO reqDTO : reqDTOS) {
            tasks.add(toEntityFromReq(reqDTO));
        }
        return tasks;
    }
}
