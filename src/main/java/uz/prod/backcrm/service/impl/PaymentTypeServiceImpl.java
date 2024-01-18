package uz.prod.backcrm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.mapper.PaymentTypeMapper;
import uz.prod.backcrm.payload.PaymentTypeDTO;
import uz.prod.backcrm.repository.PaymentTypeRepository;
import uz.prod.backcrm.service.abs.PaymentTypeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentTypeServiceImpl implements PaymentTypeService {

    private final PaymentTypeRepository paymentTypeRepository;
    private final PaymentTypeMapper paymentTypeMapper;

    @Override
    public ApiResult<List<PaymentTypeDTO>> getPaymentTypes() {
        return ApiResult.success(paymentTypeMapper.toDTOList(paymentTypeRepository.findAll()));
    }
}
