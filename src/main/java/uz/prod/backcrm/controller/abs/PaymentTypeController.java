package uz.prod.backcrm.controller.abs;

import org.springframework.web.bind.annotation.*;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.PaymentTypeDTO;
import uz.prod.backcrm.utills.constants.Rest;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(PaymentTypeController.PAYMENT_TYPE)
public interface PaymentTypeController {

    String PAYMENT_TYPE = "payment-type/";

    String GET_BY_ID = "{id}";

    String EDIT = "edit/{id}";

    String DELETE = "delete/{id}";

    @GetMapping(GET_BY_ID)
    ApiResult<PaymentTypeDTO> getById(@PathVariable Long id);

    @GetMapping
    ApiResult<List<PaymentTypeDTO>> getAll(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                           @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @PostMapping
    ApiResult<?> addPaymentType(@Valid @RequestBody PaymentTypeDTO paymentTypeDTO);

    @PutMapping(EDIT)
    ApiResult<?> editPaymentType(@PathVariable Long id,
                                 @Valid @RequestBody PaymentTypeDTO paymentTypeDTO);

    @DeleteMapping(DELETE)
    ApiResult<?> deletePaymentType(@PathVariable Long id);
}
