package br.com.familyfinance.autenticador.shared.utils;

import br.com.familyfinance.autenticador.domain.model.User;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.time.Instant;

@ApplicationScoped
public class JwtUtil {

    @ConfigProperty(name = "app.security.jwt.issuer")
    String jwtIssuer;

    @ConfigProperty(name = "app.security.jwt.duration.seconds")
    Long ducationInSeconds;

    public String generateToken(User usuario) {
        return Jwt.issuer(jwtIssuer)
                .subject(usuario.getUsername().toString())
                .upn(usuario.getEmail().toString())
                .preferredUserName(usuario.getName().toString())
                .expiresIn(Duration.ofSeconds(ducationInSeconds))
                .issuedAt(Instant.now())
                .sign();
    }

}
