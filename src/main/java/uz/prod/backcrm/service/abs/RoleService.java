package uz.prod.backcrm.service.abs;

import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.RoleDTO;

import java.util.List;

public interface RoleService {
    ApiResult<List<RoleDTO>> getRoles();
}
