package uz.prod.backcrm.service.abs;

import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.ProjectTypeDTO;

import java.util.List;

public interface ProjectTypeService {
    ApiResult<List<ProjectTypeDTO>> getProjectType();
}
