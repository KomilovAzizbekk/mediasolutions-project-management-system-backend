package uz.prod.backcrm.mapper;

import org.mapstruct.Mapper;
import uz.prod.backcrm.entity.Role;
import uz.prod.backcrm.payload.RoleDTO;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role toEntity(RoleDTO roleDTO);

}
