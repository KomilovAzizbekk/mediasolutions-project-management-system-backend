package uz.prod.backcrm.mapper;

import org.mapstruct.Mapper;
import uz.prod.backcrm.entity.ProjectType;
import uz.prod.backcrm.payload.ProjectTypeDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectTypeMapper {

    ProjectType toEntity(ProjectTypeDTO projectTypeDTO);

    ProjectTypeDTO toDTO(ProjectType projectType);

    List<ProjectTypeDTO> toDTOList(List<ProjectType> projectTypes);

}
