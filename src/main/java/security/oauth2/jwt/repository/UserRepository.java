package security.oauth2.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import security.oauth2.jwt.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String userName);
}
