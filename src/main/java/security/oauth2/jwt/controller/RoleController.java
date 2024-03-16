package security.oauth2.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import security.oauth2.jwt.entity.Role;
import security.oauth2.jwt.repository.RoleRepository;

@RestController
public class RoleController {
    @Autowired
    private RoleRepository repository;

    @GetMapping("/create-new-role")
    public String createNewRole(@RequestParam("roleName") String roleName)
    {
        // http://localhost:8080/create-new-role?roleName=USER
        roleName = roleName + "_ROLE";
        if (!repository.findByName(roleName).isEmpty()) {
            return "role " + roleName + " already exist";
        }

        Role newRole = new Role();
        newRole.setName(roleName);
        repository.save(newRole);

        return "new Role with name " + roleName + " was added";
    }
}
