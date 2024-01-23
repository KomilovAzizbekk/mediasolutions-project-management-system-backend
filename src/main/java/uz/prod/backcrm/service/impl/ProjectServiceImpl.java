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
import uz.prod.backcrm.entity.Status;
import uz.prod.backcrm.entity.User;
import uz.prod.backcrm.enums.ProjectTypeName;
import uz.prod.backcrm.enums.StatusName;
import uz.prod.backcrm.exceptions.RestException;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.mapper.*;
import uz.prod.backcrm.payload.ProjectReqDTO;
import uz.prod.backcrm.payload.ProjectResDTO;
import uz.prod.backcrm.repository.ProjectRepository;
import uz.prod.backcrm.repository.ProjectTypeRepository;
import uz.prod.backcrm.repository.StatusRepository;
import uz.prod.backcrm.repository.UserRepository;
import uz.prod.backcrm.service.abs.ProjectService;
import uz.prod.backcrm.utills.CommonUtils;
import uz.prod.backcrm.utills.constants.Message;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private final ProjectTypeRepository projectTypeRepository;

    @Override
    public ApiResult<?> addProject(ProjectReqDTO projectDTO) {
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
        return ApiResult.success(toDTO(project));
    }

    @Override
    public ApiResult<List<ProjectResDTO>> getAllProjects(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Project> projects = projectRepository.findAll(pageable);
        return ApiResult.successPageable(toDTOList(projects), projects.getTotalElements());
    }

    @Override
    public ApiResult<List<ProjectResDTO>> getMyProjects(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        User user = CommonUtils.getUserFromSecurityContext();
        Page<Project> projects = projectRepository.findAllByUsers(user, pageable);
        return ApiResult.successPageable(toDTOList(projects), projects.getTotalElements());
    }

    @Override
    public ApiResult<?> editProject(UUID id, ProjectReqDTO dto) {
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

    public ProjectResDTO toDTO(Project project) {
        if ( project == null ) {
            return null;
        }

        ProjectResDTO.ProjectResDTOBuilder projectResDTO = ProjectResDTO.builder();

        projectResDTO.id( project.getId() );
        projectResDTO.name( project.getName() );
        projectResDTO.clientName( project.getClientName() );
        projectResDTO.clientPhone1( project.getClientPhone1() );
        projectResDTO.clientPhone2( project.getClientPhone2() );
        projectResDTO.description( project.getDescription() );
        projectResDTO.price( project.getPrice() );
        projectResDTO.status( map( project.getStatus() ) );
        projectResDTO.dealNumber( project.getDealNumber() );
        projectResDTO.deadline( project.getDeadline().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) );
        projectResDTO.type( map( project.getType() ) );

        return projectResDTO.build();
    }


    public List<ProjectResDTO> toDTOList(Page<Project> projects) {
        if ( projects == null ) {
            return null;
        }

        List<ProjectResDTO> list = new ArrayList<ProjectResDTO>();
        for ( Project project : projects ) {
            list.add( toDTO( project ) );
        }

        return list;
    }

    public String map(ProjectType value) {
        if ( value == null ) {
            return null;
        }

        return String.valueOf(value.getName());
    }

    public String map(Status value) {
        if ( value == null ) {
            return null;
        }

        return String.valueOf(value.getName());
    }
}
