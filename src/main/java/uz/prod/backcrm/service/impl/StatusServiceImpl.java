package uz.prod.backcrm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.mapper.StatusMapper;
import uz.prod.backcrm.payload.StatusDTO;
import uz.prod.backcrm.repository.StatusRepository;
import uz.prod.backcrm.service.abs.StatusService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;
    private final StatusMapper statusMapper;

    @Override
    public ApiResult<List<StatusDTO>> getStatus() {
        System.out.println(statusRepository.findAll());
        return ApiResult.success(statusMapper.toDTOList(statusRepository.findAll()));
    }
}
