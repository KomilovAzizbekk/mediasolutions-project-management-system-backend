package uz.prod.backcrm.service.abs;

import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.PaymentDTO;
import uz.prod.backcrm.payload.PaymentTypeDTO;

import java.util.List;
import java.util.UUID;

public interface PaymentService {
    ApiResult<?> addPaymentsToProject(UUID pId, List<PaymentDTO> paymentDTOS);

    ApiResult<List<PaymentDTO>> getByProjectId(int page, int size, UUID id);
}
