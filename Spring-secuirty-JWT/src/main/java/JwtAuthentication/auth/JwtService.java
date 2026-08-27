package JwtAuthentication.auth;

import JwtAuthentication.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.Principal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import static javax.crypto.Cipher.SECRET_KEY;

@Component
public class JwtService {

    private final SecretKey key;

    private static final long EXPIRATION_SECONDS = 6000;

    public JwtService(@Value("${spring.jwt.secret}") String secret) {
        key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Authentication authentication) {
        Instant issuedAt = Instant.now();
        UserDetails user = (UserDetails) authentication.getPrincipal();

        return Jwts.builder().signWith(key).subject(user.getUsername()).issuedAt(Date.from(issuedAt))
                .expiration(Date.from(issuedAt.plusSeconds(EXPIRATION_SECONDS))).compact().toString();
    }

    public Claims validateToken(String token) {
        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();

        return claims;
    }
}
