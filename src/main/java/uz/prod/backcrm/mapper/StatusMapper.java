package uz.prod.backcrm.mapper;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import uz.prod.backcrm.entity.Status;
import uz.prod.backcrm.enums.StatusName;
import uz.prod.backcrm.payload.StatusDTO;

import java.util.List;


@Mapper(componentModel = "spring")
public interface StatusMapper {

    Status toEntity(String statusDTO);

    StatusDTO toDTO(Status status);

    List<StatusDTO> toDTOList(List<Status> statuses);

    StatusName map(String value);

    String map(StatusName value);



}
