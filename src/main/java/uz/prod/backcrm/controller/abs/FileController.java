package uz.prod.backcrm.controller.abs;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.FileDTO;
import uz.prod.backcrm.utills.constants.Rest;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RequestMapping(FileController.FILE)
public interface FileController {

    String FILE = Rest.BASE_PATH_V1 + "file/";

    String GET = "get/{id}";

    String UPLOAD = "/upload";

    String DELETE = "delete/{id}";

    @GetMapping(GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PM', 'ROLE_PROGRAMMER')")
    void getFile(@PathVariable UUID id, HttpServletResponse response);

    @PostMapping(UPLOAD)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PM')")
    ApiResult<List<FileDTO>> uploadFile(MultipartHttpServletRequest request);

    @DeleteMapping(DELETE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PM')")
    ApiResult<?> deleteFile(@PathVariable UUID id);

}
