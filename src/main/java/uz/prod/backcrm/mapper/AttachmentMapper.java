package uz.prod.backcrm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import uz.prod.backcrm.entity.Attachment;
import uz.prod.backcrm.payload.AttachmentDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {

    @Mappings({
            @Mapping(target = "url", source = "fileName"),
            @Mapping(target = "name", source = "originalName")
    })
    AttachmentDTO toAttachmentDTO(Attachment attachment);

    @Mappings({
            @Mapping(target = "fileName", source = "url"),
            @Mapping(target = "originalName", source = "name")
    })
    Attachment toEntity(AttachmentDTO dto);

    @Mappings({
            @Mapping(target = "fileName", source = "url"),
            @Mapping(target = "originalName", source = "name")
    })
    List<Attachment> toEntityList(List<AttachmentDTO> dtoList);
}
