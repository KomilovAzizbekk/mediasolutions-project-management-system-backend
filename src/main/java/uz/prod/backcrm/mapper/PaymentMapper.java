package uz.prod.backcrm.mapper;

import org.mapstruct.Mapper;
import uz.prod.backcrm.entity.Payments;
import uz.prod.backcrm.payload.PaymentDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    List<Payments> toEntityList(List<PaymentDTO> paymentDTOList);

}
