package uz.prod.backcrm.mapper;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import uz.prod.backcrm.entity.Client;
import uz.prod.backcrm.payload.ClientDTO;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toEntity(ClientDTO dto);

    List<ClientDTO> toDTOList(List<Client> clients);

    ClientDTO toDTO(Client client);

    List<ClientDTO> toDTOList(Page<Client> all);
}
