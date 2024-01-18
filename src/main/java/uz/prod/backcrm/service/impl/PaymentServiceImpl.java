package uz.prod.backcrm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.prod.backcrm.entity.Payments;
import uz.prod.backcrm.entity.Project;
import uz.prod.backcrm.exceptions.RestException;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.mapper.PaymentMapper;
import uz.prod.backcrm.payload.PaymentDTO;
import uz.prod.backcrm.repository.PaymentsRepository;
import uz.prod.backcrm.repository.ProjectRepository;
import uz.prod.backcrm.service.abs.PaymentService;
import uz.prod.backcrm.utills.CommonUtils;
import uz.prod.backcrm.utills.constants.Message;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentsRepository paymentsRepository;
    private final ProjectRepository projectRepository;
    private final MessageSource messageSource;
    private final PaymentMapper paymentMapper;

    @Override
    public ApiResult<?> addPaymentsToProject(UUID pId, List<PaymentDTO> paymentDTOS) {
        String message = CommonUtils.createMessage(Message.NOT_FOUND, messageSource, new Object[]{pId});
        Project project = projectRepository.findById(pId).orElseThrow(
                () -> RestException.restThrow(message, HttpStatus.BAD_REQUEST));
        List<Payments> payments = paymentsRepository.saveAll(paymentMapper.toEntityList(paymentDTOS));
        project.setPayments(payments);
        projectRepository.save(project);
        return ApiResult.success(CommonUtils.createMessage(Message.ADDED_SUCCESSFULLY, messageSource, null));
    }

    @Override
    public ApiResult<List<PaymentDTO>> getByProjectId(int page, int size, UUID id) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Payments> payments = paymentsRepository.findAllByProjectId(pageable, id);
        return ApiResult.success(paymentMapper.toDTOList(payments));
    }
}
