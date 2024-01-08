package uz.prod.backcrm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import uz.prod.backcrm.entity.Filee;
import uz.prod.backcrm.exceptions.RestException;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.mapper.FileMapper;
import uz.prod.backcrm.payload.FileDTO;
import uz.prod.backcrm.repository.FileRepository;
import uz.prod.backcrm.service.abs.FileService;
import uz.prod.backcrm.utills.CommonUtils;
import uz.prod.backcrm.utills.constants.Message;
import uz.prod.backcrm.utills.constants.Rest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final FileMapper fileMapper;
    private final MessageSource messageSource;

    @Override
    public void getFile(UUID id, HttpServletResponse response) {

        //FILENI TOPIB OLIB KELISH TOPILMASA THROW QILAMIZ
        File file = findFile(id);

        // RESPONSEGA OSHA FILE NI OLIB BERVORAMIZ
        downloadForResponse(file, response);
    }

    @Override
    public ApiResult<List<FileDTO>> uploadFile(MultipartHttpServletRequest request) {
        Iterator<String> fileNames = request.getFileNames();
        List<FileDTO> fileDTOS = new ArrayList<>();
        while (fileNames.hasNext()){
            MultipartFile file = request.getFile(fileNames.next());
            if (file != null) {
                checkFileContent(Objects.requireNonNull(file.getContentType()));

                //FILEDAN ATTACHMENTNI OLADIGAN METOHD
                Filee filee = createFile(file);

                //FILEDTO LISTGA HAMMA SAQLAGAN FILERNI SAQLAYABMIZ RESPONSE UCHUN
                fileDTOS.add(fileMapper.toDTO(filee));

                // BU METHOD PATH YASAB BERADI. YA'NI FILENI PAPKALAR ICHIGA TAXLAB SAQLAYMIZ
                Path path = createPath(filee.getFileName());

                // BERILGAN PATH GA KO'RSATILGAN YO'LGA FILENI BYTE[] NI SAQLAYDI.SAQLOLMASA EXCEPTIONGA OTILADI
                fileSaveSystem(file, path);
            }
        }
        return null;
    }

    @Override
    public ApiResult<String> deleteFile(UUID id) {
        //AGAR FILE TOPILMASA THROW GA OTAMIZ
        ifFileNotFoundThrow(id);
        fileRepository.deleteById(id);
        return ApiResult.success(CommonUtils.createMessage(Message.DELETED_SUCCESSFULLY, messageSource, new Object[]{id}));
    }


    private void ifFileNotFoundThrow(UUID id) {
        String message = CommonUtils.createMessage(Message.NOT_FOUND, messageSource, new Object[]{id});
        if (!fileRepository.existsById(id))
            throw RestException.restThrow(message, HttpStatus.BAD_REQUEST);
    }

    private File findFile(UUID id) {
        String message = CommonUtils.createMessage(Message.NOT_FOUND, messageSource, new Object[]{id});
        Filee filee = fileRepository.findById(id).orElseThrow(
                () -> RestException.restThrow(message, HttpStatus.BAD_REQUEST));
        return new File(filee.getPath(), filee.getFileName());
    }

    private void downloadForResponse(File file, HttpServletResponse resp) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            FileCopyUtils.copy(fileInputStream, resp.getOutputStream());
        } catch (FileNotFoundException e) {
            throw RestException.restThrow("FileInputStream(attachment.getName())", HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            throw RestException.restThrow("response.getOutputStream()", HttpStatus.BAD_REQUEST);
        }
    }

    public void checkFileContent(String contentType) {
        String message = CommonUtils.createMessage(Message.INCORRECT_FILE_TYPE, messageSource, new Object[]{null});
        if (!contentType.startsWith("image/"))
            throw RestException.restThrow(message, HttpStatus.BAD_REQUEST);
    }

    //MULTIPART FILENI SAVE QILIB FILE QAYTARDIM
    public Filee createFile(MultipartFile file) {
        return fileRepository.save(Filee.builder()
                .originalName(file.getOriginalFilename())
                .fileName(createFileUrl(Objects.requireNonNull(file.getOriginalFilename())))
                .size((int) file.getSize())
                //RASMNI PAPKALAR ICHIDAN TOPISH UCHUN YOL YASAB BERADIGANNI PATH GA BERDIM
                .path(collectFolder())
                .build());
    }

    // XAR BIR FILEGA UNIQUE NAME YASAB BERADIGAN METHOD
    private String createFileUrl(String originalFilename) {
        String name = UUID.randomUUID().toString();
        String[] split = originalFilename.split("\\.");
        String contentType = split[split.length - 1];
        name = name + "." + contentType;
        return name;
    }

    //PAPKALARNI YO'LINI TAXLAB YOZIB BERADI
    public String collectFolder() {
        DateFormat dateFormat = new SimpleDateFormat("MMMMMMM");
        Calendar calendar = Calendar.getInstance();
        String year = calendar.get(Calendar.YEAR) + "";
        String month = dateFormat.format(new Date());
        String day = calendar.get(Calendar.DAY_OF_MONTH) + "";
        String hour = calendar.get(Calendar.HOUR_OF_DAY) + "";
        return Rest.FILE_PATH + "/" + year + "/" + month + "/" + day + "/" + hour;
    }

    public Path createPath(String fileName) {

        //PAPKALARNI YO'LINI TAXLAB YOZIB BERADI   EX:  =>  D:\PDP\ECMA\AI
        String folder = collectFolder();

        // BERILGAN YOL BOYICHA PAPKALARNI OCHIB BERADI
        createFolder(folder);

        String pathString = folder + "/" + fileName;
        return Paths.get(pathString);
    }

    // BERILGAN YOL BOYICHA PAPKALARNI OCHIB BERADI
    private void createFolder(String folder) {
        File file = new File(folder);
        file.mkdirs();
    }

    public void fileSaveSystem(MultipartFile file, Path path) {
        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            //FILE_GET_INPUTSTREAM bundle qoshmadim hali
            throw RestException.restThrow(CommonUtils.createMessage(Message.ERROR, messageSource,
                    new Object[]{file}), HttpStatus.BAD_REQUEST);
        }
    }
}
