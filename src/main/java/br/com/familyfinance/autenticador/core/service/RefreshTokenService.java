package br.com.familyfinance.autenticador.core.service;

import br.com.familyfinance.autenticador.core.model.RefreshToken;
import br.com.familyfinance.autenticador.core.model.Usuario;
import io.smallrye.mutiny.Uni;

public interface RefreshTokenService {
    Uni<RefreshToken> createRefreshToken(Usuario usuario);

    Uni<RefreshToken> updateRefreshToken(String refreshToken);
}
