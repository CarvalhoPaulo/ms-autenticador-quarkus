package br.com.familyfinance.autenticador.domain.service;

import br.com.familyfinance.autenticador.domain.model.RefreshToken;
import br.com.familyfinance.autenticador.domain.model.User;
import io.smallrye.mutiny.Uni;

public interface RefreshTokenService {
    Uni<RefreshToken> createRefreshToken(User usuario);

    Uni<RefreshToken> updateRefreshToken(String refreshToken);
}
