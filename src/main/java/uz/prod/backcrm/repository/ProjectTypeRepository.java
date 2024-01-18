package uz.prod.backcrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.prod.backcrm.entity.ProjectType;
import uz.prod.backcrm.enums.ProjectTypeName;

public interface ProjectTypeRepository extends JpaRepository<ProjectType, Long> {

    ProjectType findByName(ProjectTypeName name);

}
