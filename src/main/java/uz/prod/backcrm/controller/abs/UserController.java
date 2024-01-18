package uz.prod.backcrm.controller.abs;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.ProfileDTO;
import uz.prod.backcrm.payload.UserDTO;
import uz.prod.backcrm.utills.constants.Rest;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping(UserController.USER)
public interface UserController {

    String USER = Rest.BASE_PATH_V1 + "user/";
    String ME = "me";
    String GET_ALL_USERS_OR_ADMINS = "all-users-or-admins";
    String GET_BY_PROJECT_ID = "by-project/{id}";
    String EDIT_BY_ADMIN = "edit/{id}/{roleId}";
    String DELETE_BY_ADMIN = "delete/{id}";
    String BLOCK = "block/{id}";
    String UNBLOCK = "unblock/{id}";
    String BLOCKED_USERS_OR_ADMINS = "blocked-users-or-admins";
    String ACTIVE_USERS_OR_ADMINS = "active-users-or-admins";


    @GetMapping(ME)
    ApiResult<UserDTO> getMe();

    @GetMapping(GET_ALL_USERS_OR_ADMINS)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ApiResult<List<UserDTO>> getAllUsersOrAdmins(@RequestParam(defaultValue = "true") boolean areUsers,
                                                 @RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                                 @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @GetMapping(BLOCKED_USERS_OR_ADMINS)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ApiResult<List<UserDTO>> getBlockedUsersOrAdmins(@RequestParam(defaultValue = "true") boolean areUsers,
                                             @RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                             @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @GetMapping(ACTIVE_USERS_OR_ADMINS)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ApiResult<List<UserDTO>> getActiveUsersOrAdmins(@RequestParam(defaultValue = "true") boolean areUsers,
                                             @RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                             @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @GetMapping(GET_BY_PROJECT_ID)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ApiResult<List<UserDTO>> getUsersByProjectId(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                                 @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size,
                                                 @PathVariable UUID id);

    @PostMapping(BLOCK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ApiResult<?> blockUser(@PathVariable UUID id);

    @PostMapping(UNBLOCK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ApiResult<?> unblockUser(@PathVariable UUID id);

    //FAQAT USERLARNI ROLINI ALMASHTIRA OLADI
    @PutMapping(EDIT_BY_ADMIN)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ApiResult<?> editByAdmin(@PathVariable UUID id, @Valid @RequestBody ProfileDTO profileDTO);

    @DeleteMapping(DELETE_BY_ADMIN)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ApiResult<?> deleteByAdmin(@PathVariable UUID id);


}
