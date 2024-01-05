package uz.prod.backcrm.mapper;

import org.mapstruct.Mapper;
import uz.prod.backcrm.entity.Status;
import uz.prod.backcrm.payload.StatusDTO;


@Mapper(componentModel = "spring")
public interface StatusMapper {

    Status toEntity(StatusDTO statusDTO);

    StatusDTO toDTO(Status status);

}
