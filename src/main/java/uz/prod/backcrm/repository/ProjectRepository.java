package uz.prod.backcrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.prod.backcrm.entity.Project;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
}
