package security.oauth2.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import security.oauth2.jwt.entity.Debug;

import java.util.List;

@Repository
public interface DebugRepository extends JpaRepository<Debug, Long> {

    List<Debug> findByName(String name);

}