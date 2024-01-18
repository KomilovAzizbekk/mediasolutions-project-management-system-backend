package uz.prod.backcrm.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import uz.prod.backcrm.controller.abs.ProjectTypeController;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.ProjectTypeDTO;
import uz.prod.backcrm.service.abs.ProjectTypeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectTypeControllerImpl implements ProjectTypeController {

    private final ProjectTypeService projectTypeService;

    @Override
    public ApiResult<List<ProjectTypeDTO>> getProjectType() {
        return projectTypeService.getProjectType();
    }
}
