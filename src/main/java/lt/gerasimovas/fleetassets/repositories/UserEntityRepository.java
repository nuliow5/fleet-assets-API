package lt.gerasimovas.fleetassets.repositories;


import lt.gerasimovas.fleetassets.entities.UserEntity;
import lt.gerasimovas.fleetassets.enumes.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    Page<UserEntity> findByRole(Role role, Pageable pageable);

//    UserEntity findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);
}
