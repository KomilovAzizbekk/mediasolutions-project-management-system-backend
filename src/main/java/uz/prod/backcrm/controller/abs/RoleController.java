package uz.prod.backcrm.controller.abs;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.RoleDTO;
import uz.prod.backcrm.utills.constants.Rest;

import java.util.List;

@RequestMapping(RoleController.ROLE)
public interface RoleController {

    String ROLE = Rest.BASE_PATH_V1 + "role/";

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ApiResult<List<RoleDTO>> getRoles();

}
