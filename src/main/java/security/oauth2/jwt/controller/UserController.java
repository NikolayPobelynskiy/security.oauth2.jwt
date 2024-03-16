package security.oauth2.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import security.oauth2.jwt.entity.Role;
import security.oauth2.jwt.service.RoleService;
import security.oauth2.jwt.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/create")
    public String createUser(@RequestParam("user") String userName, @RequestParam("password") String password) {
        userService.addUser(userName, password);

        return "create";
    }
}
