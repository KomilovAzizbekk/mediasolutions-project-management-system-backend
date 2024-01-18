package uz.prod.backcrm.controller.abs;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.PaymentDTO;
import uz.prod.backcrm.payload.PaymentTypeDTO;
import uz.prod.backcrm.utills.constants.Rest;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping(PaymentController.PAYMENT)
public interface PaymentController {

    String PAYMENT = Rest.BASE_PATH_V1 + "payment/";

    String ADD = "add/{pId}";
    String GET_BY_PROJECT_ID = "get-by/{id}";

    @PostMapping(ADD)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PM')")
    ApiResult<?> addPaymentsToProject(@PathVariable UUID pId,
                                      @Valid @RequestBody List<PaymentDTO> paymentDTOS);

    @GetMapping(GET_BY_PROJECT_ID)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PM')")
    ApiResult<List<PaymentDTO>> getPaymentsByProjectId(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size,
                                                       @PathVariable UUID id);

}
