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
    public ApiResult<List<PaymentTypeDTO>> getPaymentTypes() {
        return paymentTypeService.getPaymentTypes();
    }
}
