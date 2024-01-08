package uz.prod.backcrm.service.abs;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.FileDTO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

public interface FileService {
    void getFile(UUID id, HttpServletResponse response);

    ApiResult<List<FileDTO>> uploadFile(MultipartHttpServletRequest request);

    ApiResult<?> deleteFile(UUID id);
}
