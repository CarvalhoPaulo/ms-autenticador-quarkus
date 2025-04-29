package br.com.familyfinance.autenticador.domain.service.impl;

import br.com.familyfinance.autenticador.domain.exception.RefreshTokenExpiradoException;
import br.com.familyfinance.autenticador.domain.exception.RefreshTokenNaoEncontradoException;
import br.com.familyfinance.autenticador.domain.model.RefreshToken;
import br.com.familyfinance.autenticador.domain.model.Usuario;
import br.com.familyfinance.autenticador.domain.repository.RefreshTokenRepository;
import br.com.familyfinance.autenticador.domain.service.RefreshTokenService;
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
    public Uni<RefreshToken> createRefreshToken(Usuario usuario) {
        return repository.inserir(getNovoRefreshToken(usuario));
    }

    @Override
    @Transactional
    public Uni<RefreshToken> updateRefreshToken(String token) {
        return repository.buscarPorToken(token)
                .onItem()
                .ifNull()
                .failWith(new RefreshTokenNaoEncontradoException())
                .onItem().ifNotNull()
                .transform(Unchecked.function(refreshToken -> {
                    if (refreshToken.getDataHoraExpiracao().isBefore(LocalDateTime.now())) {
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

    private static RefreshToken getNovoRefreshToken(Usuario usuario) {
        return RefreshToken.create(usuario);
    }
}
