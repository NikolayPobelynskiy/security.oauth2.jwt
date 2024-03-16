package security.oauth2.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import security.oauth2.jwt.entity.Role;
import security.oauth2.jwt.repository.RoleRepository;

import java.util.Optional;

@Service
public class RoleService {
    final public static int ROLE_USER_ID = 1;

    @Autowired
    private RoleRepository repository;

    public Role getUserRole() {
        Optional<Role> optionalRole = repository.findById(ROLE_USER_ID);
        return optionalRole.get();
    }
}
