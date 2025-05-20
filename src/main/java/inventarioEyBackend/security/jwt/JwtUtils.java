package inventarioEyBackend.security.jwt;

import inventarioEyBackend.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    private SecretKey key;

    @PostConstruct
    public void init() {
        try {
            Path keyFile = Paths.get("jwt-key.dat");

            if (Files.exists(keyFile)) {
                // Leer la clave existente
                byte[] keyBytes = Files.readAllBytes(keyFile);
                this.key = Keys.hmacShaKeyFor(keyBytes);
            } else {
                // Generar una nueva clave
                this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
                // Guardar la clave
                Files.write(keyFile, ((SecretKey)key).getEncoded());
            }
        } catch (IOException e) {
            logger.error("Error al manejar la clave JWT: {}", e.getMessage());
            // Generar clave en memoria como fallback
            this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        }
    }

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        // Verificar si el token está vacío o es nulo
        if (authToken == null || authToken.isEmpty()) {
            logger.error("JWT token está vacío o es nulo");
            return false;
        }

        // Verificar formato básico del token JWT (debe contener 2 puntos)
        if (!authToken.contains(".") || authToken.chars().filter(ch -> ch == '.').count() != 2) {
            logger.error("Invalid JWT token: JWT strings must contain exactly 2 period characters. Found: {}",
                    authToken.chars().filter(ch -> ch == '.').count());
            return false;
        }

        try {
            // Usar la clave segura 'key' en lugar de 'jwtSecret'
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}