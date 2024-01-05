package uz.prod.backcrm.mapper;

import org.mapstruct.Mapper;
import uz.prod.backcrm.entity.Task;
import uz.prod.backcrm.payload.TaskDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDTO toDTO(Task task);

    Task toEntity(TaskDTO dto);

    List<TaskDTO> toDTOList(List<Task> tasks);

    List<Task> toEntityList(List<TaskDTO> taskDTOList);

}
