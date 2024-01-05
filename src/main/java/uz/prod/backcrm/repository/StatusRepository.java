package uz.prod.backcrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.prod.backcrm.entity.Status;
import uz.prod.backcrm.enums.StatusName;

public interface StatusRepository extends JpaRepository<Status, Long> {

}
