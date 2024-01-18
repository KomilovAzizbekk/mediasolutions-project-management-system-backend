package uz.prod.backcrm.service.abs;

import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.StatusDTO;

import java.util.List;

public interface StatusService {
    ApiResult<List<StatusDTO>> getStatus();
}
