package uz.prod.backcrm.mapper;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import uz.prod.backcrm.entity.PaymentType;
import uz.prod.backcrm.payload.PaymentTypeDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentTypeMapper {

    PaymentTypeDTO toDTO(PaymentType paymentType);

    List<PaymentTypeDTO> toDTOList(Page<PaymentType> paymentTypeList);

    PaymentType toEntity(PaymentTypeDTO paymentTypeDTO);

}
