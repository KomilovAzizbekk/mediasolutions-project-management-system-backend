package uz.prod.backcrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.prod.backcrm.entity.Client;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

    @Query(nativeQuery = true, value = "select * from client c join project p on c.id = p.client_id where p.id=:id")
    Client findClientByProjectId(UUID id);
}
