package security.oauth2.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import security.oauth2.jwt.entity.Debug;
import security.oauth2.jwt.entity.User;
import security.oauth2.jwt.repository.DebugRepository;
import security.oauth2.jwt.repository.UserRepository;
import security.oauth2.jwt.service.UserService;
import security.oauth2.jwt.utils.JwtTokenUtil;

@RestController
public class Main {

    private final DebugRepository debugRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    public Main(DebugRepository debugRepository, UserRepository userRepository) {
        this.debugRepository = debugRepository;
        this.userRepository = userRepository;
    }
    @GetMapping("/public-all")
    public String publicPage() {
        this.debugRepository.findAll().stream().forEach((k) -> {
           System.out.println(k.getId() + "->" + k.getName());
        });

        return this.debugRepository.findAll().toString();
    }

    @GetMapping("/public-all-users")
    public String publicUsersPage() {
        this.userRepository.findAll().stream().forEach((k) -> {
            System.out.println(k.getId() + "->" + k.getUsername());
        });

        return this.userRepository.findAll().toString();
    }

    @GetMapping("/public")
    public String publicPage(@RequestParam(name="id") Long id) {

        Long userId = id;

        User user = this.userRepository.findById(userId).orElseThrow();
        String username = user.getUsername();
        UserDetails userDetails = userService.loadUserByUsername(username);

        JwtTokenUtil util = new JwtTokenUtil();
        String token = util.generateToken(userDetails);

        return token;
    }

    @GetMapping("/add-new")
    public String addDebugItem(@RequestParam(name="n") String name) {
        Debug newItem = new Debug();
        newItem.setName(name);

        debugRepository.save(newItem);

        return "created";
    }
}
