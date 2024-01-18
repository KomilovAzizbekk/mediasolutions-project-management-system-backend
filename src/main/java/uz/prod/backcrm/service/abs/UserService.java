package uz.prod.backcrm.service.abs;

import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.ProfileDTO;
import uz.prod.backcrm.payload.UserDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    ApiResult<UserDTO> me();

    ApiResult<List<UserDTO>> getAllUsersOrAdmins(boolean areUsers, int page, int size);

    ApiResult<List<UserDTO>> getBlockedUsersOrAdmins(boolean areUsers, int page, int size);

    ApiResult<List<UserDTO>> getActiveUsersOrAdmins(boolean areUsers, int page, int size);

    ApiResult<?> blockUser(UUID id);

    ApiResult<?> unblockUser(UUID id);

    ApiResult<?> editByAdmin(UUID id, ProfileDTO profileDTO);

    ApiResult<?> deleteByAdmin(UUID id);

    ApiResult<List<UserDTO>> getUsersByProjectId(int page, int size, UUID id);
}
