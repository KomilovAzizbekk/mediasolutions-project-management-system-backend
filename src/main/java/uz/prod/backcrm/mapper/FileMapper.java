package uz.prod.backcrm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import uz.prod.backcrm.entity.Filee;
import uz.prod.backcrm.payload.FileDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FileMapper {

    @Mappings({
            @Mapping(target = "url", source = "fileName"),
            @Mapping(target = "name", source = "originalName")
    })
    FileDTO toDTO(Filee filee);

    @Mappings({
            @Mapping(target = "fileName", source = "url"),
            @Mapping(target = "originalName", source = "name")
    })
    Filee toEntity(FileDTO dto);

    @Mappings({
            @Mapping(target = "fileName", source = "url"),
            @Mapping(target = "originalName", source = "name")
    })
    List<Filee> toEntityList(List<FileDTO> dtoList);
}
