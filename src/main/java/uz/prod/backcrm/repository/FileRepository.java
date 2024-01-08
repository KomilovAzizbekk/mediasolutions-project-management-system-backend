package uz.prod.backcrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.prod.backcrm.entity.Filee;

import java.util.UUID;

public interface FileRepository extends JpaRepository<Filee, UUID> {
}
