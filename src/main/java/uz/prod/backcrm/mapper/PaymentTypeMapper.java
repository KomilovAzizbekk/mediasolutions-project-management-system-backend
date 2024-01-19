package uz.prod.backcrm.mapper;

import org.mapstruct.Mapper;
import uz.prod.backcrm.entity.PaymentType;
import uz.prod.backcrm.entity.ProjectType;
import uz.prod.backcrm.payload.PaymentTypeDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentTypeMapper {

    PaymentTypeDTO toDTO(PaymentType paymentType);

    List<PaymentTypeDTO> toDTOList(List<PaymentType> paymentTypes);

    ProjectType map(String value);

    String map(ProjectType value);

}
