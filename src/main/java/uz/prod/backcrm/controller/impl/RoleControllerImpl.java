package uz.prod.backcrm.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.prod.backcrm.controller.abs.RoleController;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.RoleDTO;
import uz.prod.backcrm.service.abs.RoleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoleControllerImpl implements RoleController {

    private final RoleService roleService;

    @Override
    public ApiResult<List<RoleDTO>> getRoles() {
        return roleService.getRoles();
    }
}
