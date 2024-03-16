package security.oauth2.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import security.oauth2.jwt.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String roleName);
}
