package uz.prod.backcrm.mapper;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import uz.prod.backcrm.entity.Task;
import uz.prod.backcrm.payload.TaskResDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskResDTO toDTO(Task task);

    List<TaskResDTO> toDTOList(Page<Task> tasks);


}
