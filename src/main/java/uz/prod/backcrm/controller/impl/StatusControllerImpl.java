package uz.prod.backcrm.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.prod.backcrm.controller.abs.StatusController;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.StatusDTO;
import uz.prod.backcrm.service.abs.StatusService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatusControllerImpl implements StatusController {

    private final StatusService statusService;

    @Override
    public ApiResult<List<StatusDTO>> getStatus() {
        return statusService.getStatus();
    }
}
