package uz.prod.backcrm.service.abs;

import io.swagger.annotations.Api;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.ProfileDTO;
import uz.prod.backcrm.payload.RoleDTO;
import uz.prod.backcrm.payload.UserDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    ApiResult<UserDTO> me();

    ApiResult<List<UserDTO>> getAllUsers(int page, int size);

    ApiResult<?> editProfile(ProfileDTO dto);

    ApiResult<?> editByAdmin(UUID id, RoleDTO roleDTO);

    ApiResult<?> deleteProfile();

}
