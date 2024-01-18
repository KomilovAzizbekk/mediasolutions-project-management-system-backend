package uz.prod.backcrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.prod.backcrm.entity.Attachment;

import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {


}
