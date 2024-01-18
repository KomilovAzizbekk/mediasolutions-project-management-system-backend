package uz.prod.backcrm.controller.abs;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.prod.backcrm.manual.ApiResult;
import uz.prod.backcrm.payload.ProjectTypeDTO;
import uz.prod.backcrm.utills.constants.Rest;

import java.util.List;

@RequestMapping(ProjectTypeController.PROJECT_TYPE)
public interface ProjectTypeController {
    
    String PROJECT_TYPE = Rest.BASE_PATH_V1 + "project-type/";

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PM')")
    ApiResult<List<ProjectTypeDTO>> getProjectType();
    
}
