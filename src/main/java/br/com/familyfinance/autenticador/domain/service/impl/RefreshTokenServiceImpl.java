package br.com.familyfinance.autenticador.domain.service.impl;

import br.com.familyfinance.autenticador.domain.repository.RefreshTokenRepository;
import br.com.familyfinance.autenticador.domain.service.RefreshTokenService;
import br.dev.paulocarvalho.arquitetura.domain.exception.BusinessException;
import br.dev.paulocarvalho.autenticador.domain.exception.RefreshTokenExpiradoException;
import br.dev.paulocarvalho.autenticador.domain.exception.RefreshTokenNaoEncontradoException;
import br.dev.paulocarvalho.autenticador.domain.model.RefreshToken;
import br.dev.paulocarvalho.autenticador.domain.model.User;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;

@ApplicationScoped
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Inject
    RefreshTokenRepository repository;

    @Override
    public Uni<RefreshToken> createRefreshToken(User user) throws BusinessException {
        return repository.inserir(getNovoRefreshToken(user));
    }

    @Override
    @Transactional
    public Uni<RefreshToken> updateRefreshToken(String token) {
        return repository.findByToken(token)
                .onItem()
                .ifNull()
                .failWith(new RefreshTokenNaoEncontradoException())
                .onItem().ifNotNull()
                .transform(Unchecked.function(refreshToken -> {
                    if (refreshToken.getExpirationDateTime().isBefore(LocalDateTime.now())) {
                        throw new RefreshTokenExpiradoException();
                    }
                    return refreshToken;
                }))
                .onItem()
                .transformToUni(refreshToken -> {
                    refreshToken.renovarToken();
                    return repository.alterar(refreshToken);
                });
    }

    private static RefreshToken getNovoRefreshToken(User user) {
        return RefreshToken.create(user);
    }
}
