package uz.prod.backcrm.service.impl;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.prod.backcrm.entity.Project;
import uz.prod.backcrm.exceptions.RestException;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.mapper.*;
import uz.prod.backcrm.payload.ProjectDTO;
import uz.prod.backcrm.repository.ProjectRepository;
import uz.prod.backcrm.service.abs.ProjectService;
import uz.prod.backcrm.utills.constants.Message;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final ClientMapper clientMapper;
    private final PaymentMapper paymentMapper;
    private final StatusMapper statusMapper;
    private final TaskMapper taskMapper;
    private final UserMapper userMapper;

    @Override
    public ApiResult<?> addProject(ProjectDTO projectDTO) {
        Project project = projectMapper.toEntity(projectDTO);
        projectRepository.save(project);
        return ApiResult.success(Message.SAVED_SUCCESSFULLY);
    }

    @Override
    public ApiResult<ProjectDTO> getProjectById(UUID id) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> RestException.restThrow(Message.ID_NOT_FOUND, HttpStatus.BAD_REQUEST));
        return ApiResult.success(projectMapper.toDTO(project));
    }

    @Override
    public ApiResult<List<ProjectDTO>> getAllProjects(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Project> projects = projectRepository.findAll(pageable);
        return ApiResult.success(projectMapper.toDTOList(projects));
    }

    @Override
    public ApiResult<?> editProject(UUID id, ProjectDTO dto) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> RestException.restThrow(Message.ID_NOT_FOUND, HttpStatus.BAD_REQUEST));
        project.setName(dto.getName());
        project.setDeadline(dto.getDeadline());
        project.setDealNumber(dto.getDealNumber());
        project.setDescription(dto.getDescription());
        project.setPrice(dto.getPrice());
        project.setType(dto.getType());
        project.setClient(clientMapper.toEntity(dto.getClientDTO()));
        project.setPayments(paymentMapper.toEntityList(dto.getPaymentDTOList()));
        project.setStatus(statusMapper.toEntity(dto.getStatusDTO()));
        project.setTasks(taskMapper.toEntityList(dto.getTaskDTOList()));
        project.setUsers(userMapper.toEntityList(dto.getUserDTOList()));
        projectRepository.save(project);
        return ApiResult.success(Message.UPDATED_SUCCESSFULLY);
    }

    @Override
    public ApiResult<?> deleteProject(UUID id) {
        try {
            projectRepository.deleteById(id);
            return ApiResult.success(Message.DELETED_SUCCESSFULLY);
        } catch (Exception e) {
            throw RestException.restThrow(Message.DELETE_FAILED, HttpStatus.CONFLICT);
        }
    }
}
