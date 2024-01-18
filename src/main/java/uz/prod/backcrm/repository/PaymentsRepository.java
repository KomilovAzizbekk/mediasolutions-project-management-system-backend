package uz.prod.backcrm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.prod.backcrm.entity.Payments;

import java.util.UUID;

public interface PaymentsRepository extends JpaRepository<Payments, UUID> {

    @Query(nativeQuery = true, value = "select * from payments p join project_payments pp on p.id = pp.payments_id where project_id=:id")
    Page<Payments> findAllByProjectId(Pageable pageable, UUID id);

}
