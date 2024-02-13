package security.oauth2.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import security.oauth2.jwt.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
