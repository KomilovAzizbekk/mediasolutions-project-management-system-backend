package uz.prod.backcrm.service.abs;

import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.PaymentTypeDTO;

import java.util.List;

public interface PaymentTypeService {
    ApiResult<PaymentTypeDTO> getById(Long id);

    ApiResult<List<PaymentTypeDTO>> getAll(int page, int size);

    ApiResult<?> addPaymentType(PaymentTypeDTO paymentTypeDTO);

    ApiResult<?> editPaymentType(Long id, PaymentTypeDTO paymentTypeDTO);

    ApiResult<?> deletePaymentType(Long id);

}
