package uz.prod.backcrm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.prod.backcrm.entity.PaymentType;
import uz.prod.backcrm.exceptions.RestException;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.mapper.PaymentTypeMapper;
import uz.prod.backcrm.payload.PaymentTypeDTO;
import uz.prod.backcrm.repository.PaymentTypeRepository;
import uz.prod.backcrm.service.abs.PaymentTypeService;
import uz.prod.backcrm.utills.constants.Message;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentTypeServiceImpl implements PaymentTypeService {

    private final PaymentTypeRepository paymentTypeRepository;
    private final PaymentTypeMapper paymentTypeMapper;

    @Override
    public ApiResult<PaymentTypeDTO> getById(Long id) {
        PaymentType paymentType = paymentTypeRepository.findById(id).orElseThrow(
                () -> RestException.restThrow(Message.ID_NOT_FOUND, HttpStatus.BAD_REQUEST));
        return ApiResult.success(paymentTypeMapper.toDTO(paymentType));
    }

    @Override
    public ApiResult<List<PaymentTypeDTO>> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ApiResult.success(paymentTypeMapper.toDTOList(paymentTypeRepository.findAll(pageable)));
    }

    @Override
    public ApiResult<?> addPaymentType(PaymentTypeDTO paymentTypeDTO) {
        PaymentType paymentType = paymentTypeMapper.toEntity(paymentTypeDTO);
        paymentTypeRepository.save(paymentType);
        return ApiResult.success(Message.SAVED_SUCCESSFULLY);
    }

    @Override
    public ApiResult<?> editPaymentType(Long id, PaymentTypeDTO paymentTypeDTO) {
        PaymentType paymentType = paymentTypeRepository.findById(id).orElseThrow(
                () -> RestException.restThrow(Message.ID_NOT_FOUND, HttpStatus.BAD_REQUEST));
        paymentType.setName(paymentTypeDTO.getName());
        paymentTypeRepository.save(paymentType);
        return ApiResult.success(Message.UPDATED_SUCCESSFULLY);
    }

    @Override
    public ApiResult<?> deletePaymentType(Long id) {
        try {
            paymentTypeRepository.deleteById(id);
            return ApiResult.success(Message.DELETED_SUCCESSFULLY);
        } catch (Exception e) {
            throw RestException.restThrow(Message.DELETE_FAILED, HttpStatus.CONFLICT);
        }
    }
}
