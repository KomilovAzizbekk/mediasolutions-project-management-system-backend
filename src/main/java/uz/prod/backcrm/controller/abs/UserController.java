package uz.prod.backcrm.controller.abs;

import org.springframework.web.bind.annotation.*;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.ProfileDTO;
import uz.prod.backcrm.payload.RoleDTO;
import uz.prod.backcrm.payload.UserDTO;
import uz.prod.backcrm.utills.constants.Rest;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping(UserController.USER)
public interface UserController {

    String USER = "user/";

    String ME = "me";
    String EDIT_ME = "edit-me";
    String EDIT_BY_ADMIN = "edit/{id}";
    String DELETE_ME = "delete-me";

    @GetMapping(ME)
    ApiResult<UserDTO> getMe();

    @GetMapping
    ApiResult<List<UserDTO>> getAllUsers(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                         @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @PutMapping(EDIT_ME)
    ApiResult<?> editProfile(ProfileDTO profileDTO);

    //FAQAT USERLARNI ROLINI ALMASHTIRA OLADI
    @PutMapping(EDIT_BY_ADMIN)
    ApiResult<?> editByAdmin(@PathVariable UUID id, @Valid @RequestBody RoleDTO roleDTO);

    @DeleteMapping(DELETE_ME)
    ApiResult<?> deleteProfile();


}
