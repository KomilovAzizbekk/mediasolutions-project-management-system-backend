package uz.prod.backcrm.mapper;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import uz.prod.backcrm.entity.PaymentType;
import uz.prod.backcrm.entity.Payments;
import uz.prod.backcrm.payload.PaymentDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    List<Payments> toEntityList(List<PaymentDTO> paymentDTOList);

    List<PaymentDTO> toDTOList(Page<Payments> paymentsPage);

    PaymentType map(String value);
    String map(PaymentType value);

}
