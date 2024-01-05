package uz.prod.backcrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.prod.backcrm.entity.Payments;

import java.util.UUID;

public interface PaymentsRepository extends JpaRepository<Payments, UUID> {
}
