package uz.prod.backcrm.mapper;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import uz.prod.backcrm.entity.Project;
import uz.prod.backcrm.entity.ProjectType;
import uz.prod.backcrm.payload.ProjectResDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    Project toEntity(ProjectResDTO projectDTO);

    ProjectResDTO toDTO(Project project);

    List<ProjectResDTO> toDTOList(Page<Project> projects);

    ProjectType map(String value);

    String map(ProjectType value);

}
