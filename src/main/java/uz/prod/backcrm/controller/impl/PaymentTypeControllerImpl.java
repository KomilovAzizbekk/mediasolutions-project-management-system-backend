package uz.prod.backcrm.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.prod.backcrm.controller.abs.PaymentTypeController;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.PaymentTypeDTO;
import uz.prod.backcrm.service.abs.PaymentTypeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PaymentTypeControllerImpl implements PaymentTypeController {

    private final PaymentTypeService paymentTypeService;

    @Override
    public ApiResult<PaymentTypeDTO> getById(Long id) {
        return paymentTypeService.getById(id);
    }

    @Override
    public ApiResult<List<PaymentTypeDTO>> getAll(int page, int size) {
        return paymentTypeService.getAll(page, size);
    }

    @Override
    public ApiResult<?> addPaymentType(PaymentTypeDTO paymentTypeDTO) {
        return paymentTypeService.addPaymentType(paymentTypeDTO);
    }

    @Override
    public ApiResult<?> editPaymentType(Long id, PaymentTypeDTO paymentTypeDTO) {
        return paymentTypeService.editPaymentType(id, paymentTypeDTO);
    }

    @Override
    public ApiResult<?> deletePaymentType(Long id) {
        return paymentTypeService.deletePaymentType(id);
    }
}
