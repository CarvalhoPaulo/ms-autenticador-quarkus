package br.com.familyfinance.autenticador.domain.service;

import br.dev.paulocarvalho.arquitetura.domain.exception.BusinessException;
import br.dev.paulocarvalho.autenticador.domain.model.RefreshToken;
import br.dev.paulocarvalho.autenticador.domain.model.User;
import io.smallrye.mutiny.Uni;

public interface RefreshTokenService {
    Uni<RefreshToken> createRefreshToken(User user) throws BusinessException;

    Uni<RefreshToken> updateRefreshToken(String refreshToken);
}
