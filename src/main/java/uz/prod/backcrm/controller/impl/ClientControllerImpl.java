package uz.prod.backcrm.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.prod.backcrm.controller.abs.ClientController;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.ClientDTO;
import uz.prod.backcrm.service.abs.ClientService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ClientControllerImpl implements ClientController {

    private final ClientService clientService;

    @Override
    public ApiResult<ClientDTO> getClientById(UUID id) {
        return clientService.getClientById(id);
    }

    @Override
    public ApiResult<ClientDTO> getByProjectId(UUID id) {
        return clientService.getByProjectId(id);
    }

    @Override
    public ApiResult<List<ClientDTO>> getAllClients(int page, int size) {
        return clientService.getAllClients(page, size);
    }

    @Override
    public ApiResult<?> addClient(ClientDTO dto) {
        return clientService.addClient(dto);
    }

    @Override
    public ApiResult<?> editClient(UUID id, ClientDTO dto) {
        return clientService.updateClient(id, dto);
    }

    @Override
    public ApiResult<?> deleteClient(UUID id) {
        return clientService.deleteClient(id);
    }
}
