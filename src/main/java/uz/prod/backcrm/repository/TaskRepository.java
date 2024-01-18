package uz.prod.backcrm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.prod.backcrm.entity.Task;
import uz.prod.backcrm.entity.User;
import uz.prod.backcrm.enums.StatusName;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Query(nativeQuery = true, value = "select * from tasks t join project_tasks pt on t.id = pt.tasks_id where pt.project_id=:id")
    Page<Task> getAllByProjectId(UUID id, Pageable pageable);

    Page<Task> findAllByUsers(User user, Pageable pageable);

    Page<Task> findAllByStatus(Pageable pageable, StatusName satatus);

}
