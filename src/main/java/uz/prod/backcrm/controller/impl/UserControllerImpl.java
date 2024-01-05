package uz.prod.backcrm.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.prod.backcrm.controller.abs.UserController;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.ProfileDTO;
import uz.prod.backcrm.payload.RoleDTO;
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
    public ApiResult<List<UserDTO>> getAllUsers(int page, int size) {
        return userService.getAllUsers(page, size);
    }

    @Override
    public ApiResult<?> editProfile(ProfileDTO profileDTO) {
        return userService.editProfile(profileDTO);
    }

    @Override
    public ApiResult<?> editByAdmin(UUID id, RoleDTO roleDTO) {
        return userService.editByAdmin(id, roleDTO);
    }

    @Override
    public ApiResult<?> deleteProfile() {
        return userService.deleteProfile();
    }
}
