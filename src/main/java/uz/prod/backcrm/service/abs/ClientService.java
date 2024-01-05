package uz.prod.backcrm.service.abs;

import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.ClientDTO;

import java.util.List;
import java.util.UUID;

public interface ClientService {

    ApiResult<?> addClient(ClientDTO clientDTO);

    ApiResult<List<ClientDTO>> getAllClients(int page, int size);

    ApiResult<ClientDTO> getByProjectId(UUID id);

    ApiResult<ClientDTO> getClientById(UUID id);

    ApiResult<?> updateClient(UUID id, ClientDTO clientDTO);

    ApiResult<?> deleteClient(UUID id);
}
