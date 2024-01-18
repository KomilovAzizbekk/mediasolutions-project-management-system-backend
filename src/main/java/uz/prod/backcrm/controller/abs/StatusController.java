package uz.prod.backcrm.controller.abs;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.StatusDTO;
import uz.prod.backcrm.utills.constants.Rest;

import java.util.List;

@RequestMapping(StatusController.STATUS)
public interface StatusController {

    String STATUS = Rest.BASE_PATH_V1 + "status/";

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PM')")
    ApiResult<List<StatusDTO>> getStatus();

}
