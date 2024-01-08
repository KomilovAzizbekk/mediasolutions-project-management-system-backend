package uz.prod.backcrm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.prod.backcrm.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

     boolean existsByUsername(String username);

     @Query(nativeQuery = true, value = "select * from users u where u.role_id!=1")
     Page<User> findAllUsers(Pageable pageable);

     @Query(nativeQuery = true, value = "select * from users u where u.role_id=1")
     Page<User> findAllAdmins(Pageable pageable);

     Optional<User> findFirstByUsernameAndEnabledIsTrueAndAccountNonExpiredIsTrueAndAccountNonLockedIsTrueAndCredentialsNonExpiredIsTrue(String username);

     Optional<User> findByUsername(String username);

     Boolean existsByPhoneNumberOrUsername(String phoneNumber, String username);

     Optional<User> findByIdAndEnabledIsTrueAndAccountNonExpiredIsTrueAndCredentialsNonExpiredIsTrueAndAccountNonLockedIsTrue(UUID userId);

     Page<User> findAllByAccountNonLockedIsTrueAndRoleId(Pageable pageable, Long role_id);

     Page<User> findAllByAccountNonLockedIsTrueAndRoleIdNot(Pageable pageable, Long role_id);

     Page<User> findAllByAccountNonLockedIsFalseAndRoleId(Pageable pageable, Long role_id);

     Page<User> findAllByAccountNonLockedIsFalseAndRoleIdNot(Pageable pageable, Long role_id);

}
