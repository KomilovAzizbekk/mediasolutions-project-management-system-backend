package uz.prod.backcrm.controller.abs;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.ClientDTO;
import uz.prod.backcrm.utills.constants.Rest;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping(ClientController.CLIENT)
public interface ClientController {
    String CLIENT = Rest.BASE_PATH_V1 + "client/";

    String GET_BY_ID = "{id}";

    String GET_P_ID = "by-project/{id}";

    String EDIT = "edit/{id}";

    String DELETE = "delete/{id}";

    @GetMapping(GET_BY_ID)
    ApiResult<ClientDTO> getClientById(@PathVariable UUID id);

    @GetMapping(GET_P_ID)
    ApiResult<ClientDTO> getByProjectId(@PathVariable UUID id);

    @GetMapping
    ApiResult<List<ClientDTO>> getAllClients(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                             @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @PostMapping
    ApiResult<?> addClient(@Valid @RequestBody ClientDTO clientDTO);

    @PutMapping(EDIT)
    ApiResult<?> editClient(@PathVariable UUID id, @Valid @RequestBody ClientDTO clientDTO);

    @DeleteMapping(DELETE)
    ApiResult<?> deleteClient(@PathVariable UUID id);



}
