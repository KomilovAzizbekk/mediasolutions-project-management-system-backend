package uz.prod.backcrm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.prod.backcrm.entity.Project;
import uz.prod.backcrm.entity.User;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {

    Page<Project> findAllByUsers(User user, Pageable pageable);
}
