package security.oauth2.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import security.oauth2.jwt.entity.Role;
import security.oauth2.jwt.entity.User;
import security.oauth2.jwt.repository.RoleRepository;
import security.oauth2.jwt.repository.UserRepository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roles;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("Can not find user with username=%s", username)
                ));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }

    public User addUser(String username, String password)
    {
        User user = new User();
        user.setUsername(username);
        java.util.List<Role> roles = new ArrayList<Role>();
        roles.add(this.roles.getUserRole());
        user.setRoles(roles);
        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();

        user.setPassword(enc.encode(password));
        userRepository.save(user);

        return user;
    }
}
