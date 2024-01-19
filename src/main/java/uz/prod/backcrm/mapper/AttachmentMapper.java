package uz.prod.backcrm.mapper;

import org.mapstruct.Mapper;
import uz.prod.backcrm.entity.Attachment;
import uz.prod.backcrm.payload.AttachmentDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {

    List<Attachment> toEntityList(List<AttachmentDTO> attachmentDTOS);

    Attachment map(String value);

}
