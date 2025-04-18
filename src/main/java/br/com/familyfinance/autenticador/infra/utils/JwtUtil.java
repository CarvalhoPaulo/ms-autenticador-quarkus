package br.com.familyfinance.autenticador.infra.utils;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JwtUtil {

    public String generateToken(String username) {
        return Jwt.issuer("http://familyfinance.com.br")
                .subject(username)
                .sign();
    }

}
