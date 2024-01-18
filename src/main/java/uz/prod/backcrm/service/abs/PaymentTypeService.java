package uz.prod.backcrm.service.abs;

import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.PaymentTypeDTO;

import java.util.List;

public interface PaymentTypeService {
    ApiResult<List<PaymentTypeDTO>> getPaymentTypes();
}
