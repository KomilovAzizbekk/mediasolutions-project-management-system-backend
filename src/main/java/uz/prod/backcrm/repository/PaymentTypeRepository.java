package uz.prod.backcrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.prod.backcrm.entity.PaymentType;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {
}
