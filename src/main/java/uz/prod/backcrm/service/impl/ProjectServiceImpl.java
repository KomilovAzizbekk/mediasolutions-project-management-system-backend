package uz.prod.backcrm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.prod.backcrm.entity.Project;
import uz.prod.backcrm.entity.User;
import uz.prod.backcrm.exceptions.RestException;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.mapper.*;
import uz.prod.backcrm.payload.ProjectDTO;
import uz.prod.backcrm.repository.ProjectRepository;
import uz.prod.backcrm.service.abs.ProjectService;
import uz.prod.backcrm.utills.CommonUtils;
import uz.prod.backcrm.utills.constants.Message;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final PaymentMapper paymentMapper;
    private final StatusMapper statusMapper;
    private final TaskMapper taskMapper;
    private final UserMapper userMapper;
    private final MessageSource messageSource;

    @Override
    public ApiResult<?> addProject(ProjectDTO projectDTO) {
        Project project = projectMapper.toEntity(projectDTO);
        projectRepository.save(project);
        return ApiResult.success(CommonUtils.createMessage(Message.ADDED_SUCCESSFULLY, messageSource, new Object[]{projectDTO}));
    }

    @Override
    public ApiResult<ProjectDTO> getProjectById(UUID id) {
        String message = CommonUtils.createMessage(Message.NOT_FOUND, messageSource, new Object[]{id});
        Project project = projectRepository.findById(id).orElseThrow(
                () -> RestException.restThrow(message, HttpStatus.BAD_REQUEST));
        return ApiResult.success(projectMapper.toDTO(project));
    }

    @Override
    public ApiResult<List<ProjectDTO>> getAllProjects(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Project> projects = projectRepository.findAll(pageable);
        return ApiResult.success(projectMapper.toDTOList(projects));
    }

    @Override
    public ApiResult<List<ProjectDTO>> getMyProjects(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        User user = CommonUtils.getUserFromRequest();
        return ApiResult.success(projectMapper.toDTOList(projectRepository.findAllByUsers(user, pageable)));
    }

    @Override
    public ApiResult<?> editProject(UUID id, ProjectDTO dto) {
        String message = CommonUtils.createMessage(Message.NOT_FOUND, messageSource, new Object[]{id});
        Project project = projectRepository.findById(id).orElseThrow(
                () -> RestException.restThrow(message, HttpStatus.BAD_REQUEST));
        project.setName(dto.getName());
        project.setDeadline(dto.getDeadline());
        project.setDealNumber(dto.getDealNumber());
        project.setDescription(dto.getDescription());
        project.setPrice(dto.getPrice());
        project.setType(dto.getType());
        project.setClientName(dto.getClientName());
        project.setClientPhone1(dto.getClientPhone1());
        project.setClientPhone2(dto.getClientPhone2());
        project.setPayments(paymentMapper.toEntityList(dto.getPaymentDTOS()));
        project.setStatus(statusMapper.toEntity(dto.getStatusDTO()));
        project.setTasks(taskMapper.toEntityList(dto.getTaskDTOS()));
        project.setUsers(userMapper.toEntityList(dto.getUserDTOS()));
        projectRepository.save(project);
        return ApiResult.success(CommonUtils.createMessage(Message.UPDATED_SUCCESSFULLY, messageSource, new Object[]{id}));
    }

    @Override
    public ApiResult<?> deleteProject(UUID id) {
        try {
            projectRepository.deleteById(id);
            return ApiResult.success(CommonUtils.createMessage(Message.DELETED_SUCCESSFULLY,
                    messageSource, new Object[]{id}));
        } catch (Exception e) {
            throw RestException.restThrow(CommonUtils.createMessage(Message.DELETE_FAILED,
                    messageSource, new Object[]{id}), HttpStatus.CONFLICT);
        }
    }
}
