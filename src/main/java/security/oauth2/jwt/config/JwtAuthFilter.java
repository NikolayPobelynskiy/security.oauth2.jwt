package security.oauth2.jwt.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import security.oauth2.jwt.service.UserService;
import security.oauth2.jwt.utils.JwtTokenUtil;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if ((authHeader != null) && authHeader.startsWith("Bearer ")) {
            authHeader = authHeader.substring(7);
            JwtTokenUtil util = new JwtTokenUtil();
            String userName = util.getUserNameByToken(authHeader);
            UserDetails userDetail = userService.loadUserByUsername(userName);

            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();
            
            UsernamePasswordAuthenticationToken authReq
                    = new UsernamePasswordAuthenticationToken(userDetail.getUsername(), userDetail.getPassword());
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(authReq);

            //Authentication ath = SecurityContextHolder.getContext().getAuthentication();
        }

        filterChain.doFilter(request, response);
    }
}
