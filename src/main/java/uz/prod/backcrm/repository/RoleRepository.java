package uz.prod.backcrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.prod.backcrm.entity.Role;
import uz.prod.backcrm.enums.RoleName;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(RoleName roleName);
}
