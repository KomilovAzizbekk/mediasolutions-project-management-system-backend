package uz.prod.backcrm.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.prod.backcrm.controller.abs.PaymentController;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.PaymentDTO;
import uz.prod.backcrm.service.abs.PaymentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PaymentControllerImpl implements PaymentController {

    private final PaymentService paymentService;

    @Override
    public ApiResult<?> addPaymentsToProject(UUID pId, List<PaymentDTO> paymentDTOS) {
        return paymentService.addPaymentsToProject(pId, paymentDTOS);
    }

    @Override
    public ApiResult<List<PaymentDTO>> getPaymentsByProjectId(int page, int size, UUID id) {
        return paymentService.getByProjectId(page, size, id);
    }
}
