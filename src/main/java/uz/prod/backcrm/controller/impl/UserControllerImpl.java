package uz.prod.backcrm.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.prod.backcrm.controller.abs.UserController;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.ProfileDTO;
import uz.prod.backcrm.payload.UserDTO;
import uz.prod.backcrm.service.abs.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ApiResult<UserDTO> getMe() {
        return userService.me();
    }

    @Override
    public ApiResult<List<UserDTO>> getAllUsersOrAdmins(boolean areUsers, int page, int size) {
        return userService.getAllUsersOrAdmins(areUsers, page, size);
    }

    @Override
    public ApiResult<List<UserDTO>> getBlockedUsersOrAdmins(boolean areUsers, int page, int size) {
        return userService.getBlockedUsersOrAdmins(areUsers, page, size);
    }

    @Override
    public ApiResult<List<UserDTO>> getActiveUsersOrAdmins(boolean areUsers, int page, int size) {
        return userService.getActiveUsersOrAdmins(areUsers, page, size);
    }

    @Override
    public ApiResult<List<UserDTO>> getUsersByProjectId(int page, int size, UUID id) {
        return userService.getUsersByProjectId(page, size, id);
    }

    @Override
    public ApiResult<?> blockUser(UUID id) {
        return userService.blockUser(id);
    }

    @Override
    public ApiResult<?> unblockUser(UUID id) {
        return userService.unblockUser(id);
    }

    @Override
    public ApiResult<?> editByAdmin(UUID id, ProfileDTO profileDTO) {
        return userService.editByAdmin(id, profileDTO);
    }

    @Override
    public ApiResult<?> deleteByAdmin(UUID id) {
        return userService.deleteByAdmin(id);
    }

}
