package microservice.pratice.Security.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import microservice.pratice.User.Models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    public String genrateToken(User requester) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("email",requester.getEmail());

//        int days = 10;
//        Date futureDate = new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * days);

        Date futureDate = new Date(System.currentTimeMillis() + 1000L * 60 * 10); // 1 minute


        return Jwts
                .builder()
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(futureDate)
                .signWith(getKey())
                .compact();
    }

    public String genrateAccessToken(User requester) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("email",requester.getEmail());

        Date futureDate = new Date(System.currentTimeMillis() + 1000L * 60 * 10); // 1 minute

        return Jwts
                .builder()
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(futureDate)
                .signWith(getKey())
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // --- parsing helpers ---

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith((javax.crypto.SecretKey) getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            // Claims are still available on the exception even though token is expired
            return e.getClaims();
        }

//        return Jwts.parser()
//                .verifyWith((javax.crypto.SecretKey) getKey())
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
    }

    public String extractEmail(String token) {
        return extractClaim(token, claims -> claims.get("email",String.class));
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validate(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

}
