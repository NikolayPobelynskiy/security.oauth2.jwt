package security.oauth2.jwt.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public class JwtTokenUtil {
    public String generateToken(UserDetails userDetails)
    {
        long lifeTime = 10 * 60 * 60L;
        String secret = "rtjkgneothn";


        Date isseDate = new Date();
        Date endDate = new Date(isseDate.getTime() + lifeTime);

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(isseDate)
                .expiration(endDate)
                .signWith(SignatureAlgorithm.ES256, secret)
                .compact();
    }
}
