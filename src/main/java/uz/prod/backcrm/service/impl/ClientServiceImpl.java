package uz.prod.backcrm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.prod.backcrm.component.MessageService;
import uz.prod.backcrm.entity.Client;
import uz.prod.backcrm.exceptions.RestException;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.mapper.ClientMapper;
import uz.prod.backcrm.payload.ClientDTO;
import uz.prod.backcrm.repository.ClientRepository;
import uz.prod.backcrm.service.abs.ClientService;
import uz.prod.backcrm.utills.constants.Message;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public ApiResult<?> addClient(ClientDTO clientDTO) {
        Client client = clientMapper.toEntity(clientDTO);
        clientRepository.save(client);
        return ApiResult.success(MessageService.getMessage(Message.SAVED_SUCCESSFULLY));
    }

    @Override
    public ApiResult<List<ClientDTO>> getAllClients(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<ClientDTO> clientDTOS = clientMapper
                .toDTOList(clientRepository.findAll(pageable));
        return ApiResult.success(clientDTOS);
    }

    @Override
    public ApiResult<ClientDTO> getByProjectId(UUID id) {
        return ApiResult.success(clientMapper.toDTO(clientRepository.findClientByProjectId(id)));
    }

    @Override
    public ApiResult<ClientDTO> getClientById(UUID id) {
        Client client = clientRepository.findById(id).orElseThrow(
                () -> RestException.restThrow(Message.ID_NOT_FOUND, HttpStatus.BAD_REQUEST));
        return ApiResult.success(clientMapper.toDTO(client));
    }

    @Override
    public ApiResult<?> updateClient(UUID id, ClientDTO clientDTO) {
        Client client = clientRepository.findById(id).orElseThrow(
                () -> RestException.restThrow(Message.ID_NOT_FOUND, HttpStatus.BAD_REQUEST));
        client.setFullName(clientDTO.getFullName());
        client.setPhoneNumber1(clientDTO.getPhoneNumber1());
        client.setPhoneNumber2(clientDTO.getPhoneNumber2());
        clientRepository.save(client);
        return ApiResult.success(Message.UPDATED_SUCCESSFULLY);
    }

    @Override
    public ApiResult<?> deleteClient(UUID id) {
        try {
            clientRepository.deleteById(id);
            return ApiResult.success(Message.DELETED_SUCCESSFULLY);
        } catch (Exception e){
            throw RestException.restThrow(Message.DELETE_FAILED, HttpStatus.CONFLICT);
        }
    }

}
