package uz.prod.backcrm.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.prod.backcrm.controller.abs.FileController;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.FileDTO;
import uz.prod.backcrm.service.abs.FileService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class FileControllerImpl implements FileController {

    private final FileService fileService;

    @Override
    public void getFile(UUID id, HttpServletResponse response) {
        fileService.getFile(id, response);
    }

    @Override
    public ApiResult<List<FileDTO>> uploadFile(MultipartHttpServletRequest request) {
        return fileService.uploadFile(request);
    }

    @Override
    public ApiResult<?> deleteFile(UUID id) {
        return fileService.deleteFile(id);
    }
}
