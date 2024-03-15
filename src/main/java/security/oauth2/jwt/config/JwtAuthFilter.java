package security.oauth2.jwt.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import security.oauth2.jwt.utils.JwtTokenUtil;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        filterChain.doFilter(request, response);



        if (!authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            System.out.println(authHeader);
            authHeader = authHeader.substring(7);

            JwtTokenUtil util = new JwtTokenUtil();
            util.parseToken(authHeader);
           // System.out.println(util.parseToken(authHeader));
        }

        System.out.println(SecurityContextHolder.getContext().getAuthentication());

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            if (!authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
                System.out.println(authHeader);
                authHeader = authHeader.substring(7);
                System.out.println(authHeader);
            }


            // username, null, listof roles
           // UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken();
            // SecurityContextHolder.getContext().setAuthentication(token);
        }
    }
}
