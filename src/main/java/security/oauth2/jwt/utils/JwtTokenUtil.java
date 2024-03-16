package security.oauth2.jwt.utils;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
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
        long lifeTime = 10 * 60 * 60 * 60L;

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

    public void parseToken(String token)
    {
        SecretKey secretKeySpec = getSigningKey();
        JwtParser jwtParser = Jwts.parser()
                .verifyWith(secretKeySpec)
                .build();

        Jwt<?, ?> res = jwtParser.parse(token);

       // Object t = Jwts.parser().verifyWith(secretKeySpec).build().parseSignedClaims(token).getPayload();
        System.out.println(Jwts.parser().verifyWith(secretKeySpec).build().parseSignedClaims(token).getPayload().get("sub"));

        System.out.println(res.getPayload());
        // List payload = (List)res.getPayload();
        // System.out.println(payload.get(0));
        // https://www.baeldung.com/java-jwt-token-decode
        // https://www.baeldung.com/java-json-web-tokens-jjwt
        // http://localhost:8080/public?id=1
        // http://localhost:8080/parse-token?token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJudm9udiIsImlhdCI6MTcwOTA2OTY4MSwiZXhwIjoxNzA5MDY5NzE3fQ.zLZXh1hywmNskfDrAhWzpaqo3KcQvZy2nIpwcVxewtk
// header={alg=HS256},payload={sub=nvonv, iat=1709069681, exp=1709069717},signature=zLZXh1hywmNskfDrAhWzpaqo3KcQvZy2nIpwcVxewtk

//        Jwts.parser().decryptWith(secret.getBytes())
    }
}
