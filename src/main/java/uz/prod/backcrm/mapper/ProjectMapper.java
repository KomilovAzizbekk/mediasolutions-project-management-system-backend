package uz.prod.backcrm.mapper;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import uz.prod.backcrm.entity.Project;
import uz.prod.backcrm.payload.ProjectDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    Project toEntity(ProjectDTO projectDTO);

    ProjectDTO toDTO(Project project);

    List<ProjectDTO> toDTOList(Page<Project> projects);

}
