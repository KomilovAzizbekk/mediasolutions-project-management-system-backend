package uz.prod.backcrm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.mapper.ProjectTypeMapper;
import uz.prod.backcrm.payload.ProjectTypeDTO;
import uz.prod.backcrm.repository.ProjectTypeRepository;
import uz.prod.backcrm.service.abs.ProjectTypeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectTypeServiceImpl implements ProjectTypeService {

    private final ProjectTypeRepository projectTypeRepository;
    private final ProjectTypeMapper projectTypeMapper;

    @Override
    public ApiResult<List<ProjectTypeDTO>> getProjectType() {
        return ApiResult.success(projectTypeMapper.toDTOList(projectTypeRepository.findAll()));
    }
}
