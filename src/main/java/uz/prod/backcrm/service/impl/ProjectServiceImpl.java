package uz.prod.backcrm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.prod.backcrm.entity.Project;
import uz.prod.backcrm.entity.ProjectType;
import uz.prod.backcrm.entity.User;
import uz.prod.backcrm.enums.ProjectTypeName;
import uz.prod.backcrm.enums.StatusName;
import uz.prod.backcrm.exceptions.RestException;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.mapper.*;
import uz.prod.backcrm.payload.ProjectResDTO;
import uz.prod.backcrm.repository.ProjectRepository;
import uz.prod.backcrm.repository.ProjectTypeRepository;
import uz.prod.backcrm.repository.StatusRepository;
import uz.prod.backcrm.repository.UserRepository;
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
    private final MessageSource messageSource;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
    private final ProjectTypeMapper projectTypeMapper;
    private final ProjectTypeRepository projectTypeRepository;

    @Override
    public ApiResult<?> addProject(ProjectResDTO projectDTO) {
        Project project = projectMapper.toEntity(projectDTO);
        project.setStatus(statusRepository.findByName(StatusName.CREATED));
        ProjectType projectType = projectTypeRepository.findByName(ProjectTypeName.valueOf(projectDTO.getType()));
        project.setType(projectType);
        projectRepository.save(project);
        return ApiResult.success(CommonUtils.createMessage(Message.ADDED_SUCCESSFULLY, messageSource, new Object[]{projectDTO}));
    }

    @Override
    public ApiResult<ProjectResDTO> getProjectById(UUID id) {
        String message = CommonUtils.createMessage(Message.NOT_FOUND, messageSource, new Object[]{id});
        Project project = projectRepository.findById(id).orElseThrow(
                () -> RestException.restThrow(message, HttpStatus.BAD_REQUEST));
        return ApiResult.success(projectMapper.toDTO(project));
    }

    @Override
    public ApiResult<List<ProjectResDTO>> getAllProjects(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Project> projects = projectRepository.findAll(pageable);
        return ApiResult.success(projectMapper.toDTOList(projects));
    }

    @Override
    public ApiResult<List<ProjectResDTO>> getMyProjects(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        User user = CommonUtils.getUserFromSecurityContext();
        return ApiResult.success(projectMapper.toDTOList(projectRepository.findAllByUsers(user, pageable)));
    }

    @Override
    public ApiResult<?> editProject(UUID id, ProjectResDTO dto) {
        String message = CommonUtils.createMessage(Message.NOT_FOUND, messageSource, new Object[]{id});
        Project project = projectRepository.findById(id).orElseThrow(
                () -> RestException.restThrow(message, HttpStatus.BAD_REQUEST));
        project.setName(dto.getName());
        project.setDeadline(dto.getDeadline());
        project.setDealNumber(dto.getDealNumber());
        project.setDescription(dto.getDescription());
        project.setPrice(dto.getPrice());
        ProjectType projectType = projectTypeRepository.findByName(ProjectTypeName.valueOf(dto.getType()));
        project.setType(projectType);
        project.setClientName(dto.getClientName());
        project.setClientPhone1(dto.getClientPhone1());
        project.setClientPhone2(dto.getClientPhone2());
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

    @Override
    public ApiResult<?> addUserToProject(List<UUID> userIdList, UUID pId) {
        String message = CommonUtils.createMessage(Message.NOT_FOUND, messageSource, new Object[]{pId});
        Project project = projectRepository.findById(pId).orElseThrow(
                () -> RestException.restThrow(message, HttpStatus.BAD_REQUEST));
        project.setUsers(userRepository.findAllById(userIdList));
        projectRepository.save(project);
        return ApiResult.success(CommonUtils.createMessage(Message.ADDED_SUCCESSFULLY, messageSource, null));
    }
}
