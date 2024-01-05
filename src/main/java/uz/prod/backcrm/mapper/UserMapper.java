package uz.prod.backcrm.mapper;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import uz.prod.backcrm.entity.User;
import uz.prod.backcrm.payload.UserDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDTO userDTO);

    List<User> toEntityList(List<UserDTO> dtoList);

    UserDTO toDTO(User user);

    List<UserDTO> toDTOList(Page<User> users);

}
