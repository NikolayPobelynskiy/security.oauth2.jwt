package security.oauth2.jwt.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtTokenUtil {

    private final String secret = "iko0980ervelecmpoiko0980ervelecmpoiko0980ervelecmpo";

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails)
    {
        long lifeTime = 100 * 10 * 60 * 60 * 60L;

        Date isseDate = new Date();
        Date endDate = new Date(isseDate.getTime() + lifeTime);
        String userAuthoritiesAsString = userDetails.getAuthorities().stream().map(Object::toString).collect(Collectors.joining("-"));
        System.out.println(userAuthoritiesAsString);

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(isseDate)
                .expiration(endDate)
                .claim("roles", userAuthoritiesAsString)
                .signWith(this.getSigningKey())
                .compact();
    }

    public Jws<Claims> tokenToClaims(String token)
    {
        SecretKey secretKeySpec = getSigningKey();
        return Jwts.parser().verifyWith(secretKeySpec).build().parseSignedClaims(token);
    }

    public String getUserNameByToken(String token)
    {
        Jws<Claims> claims = tokenToClaims(token);

        return claims.getPayload().get("sub").toString();
    }

    public List getUserRolesByToken(String token)
    {
        Jws<Claims> claims = tokenToClaims(token);
        
        List roles = new ArrayList<String>();
        roles.add(claims.getPayload().get("roles").toString());

        return roles;
    }
}
