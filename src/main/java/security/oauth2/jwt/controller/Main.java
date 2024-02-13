package security.oauth2.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import security.oauth2.jwt.entity.Debug;
import security.oauth2.jwt.entity.User;
import security.oauth2.jwt.repository.DebugRepository;
import security.oauth2.jwt.service.UserService;

@RestController
public class Main {
    private final DebugRepository debugRepository;

    @Autowired
    private UserService userService;
    @Autowired
    public Main(DebugRepository debugRepository) {
        this.debugRepository = debugRepository;
    }
    @GetMapping("/public")
    public String publicPage() {
        // RoleService roleService = new RoleService();

        this.debugRepository.findAll().stream().forEach((k) -> {
           System.out.println(k.getId() + "->" + k.getName());
        });

        return this.debugRepository.findAll().toString();
    }

    @GetMapping("/create-user")
    public String createUser(@RequestParam(name="n") String name, @RequestParam(name="psw") String password)
    {
        // http://localhost:8080/create-user?n=nvonv&psw=password
        User user = userService.addUser(name, password);
        return user.getId().toString();
    }

    @GetMapping("/add-new")
    public String addDebugItem(@RequestParam(name="n") String name) {
        Debug newItem = new Debug();
        newItem.setName(name);

        debugRepository.save(newItem);

        return "created";
    }
}
