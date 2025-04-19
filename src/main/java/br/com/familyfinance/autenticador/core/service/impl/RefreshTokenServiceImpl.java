package br.com.familyfinance.autenticador.core.service.impl;

import br.com.familyfinance.arquitetura.core.exception.ArquiteturaErrorCodeEnum;
import br.com.familyfinance.arquitetura.core.exception.BusinessException;
import br.com.familyfinance.autenticador.core.model.RefreshToken;
import br.com.familyfinance.autenticador.core.model.Usuario;
import br.com.familyfinance.autenticador.core.repository.RefreshTokenRepository;
import br.com.familyfinance.autenticador.core.service.RefreshTokenService;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

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
                .failWith(new BusinessException(ArquiteturaErrorCodeEnum.TOKEN_INVALIDO, "Refresh token não encontrado"))
                .onItem().ifNotNull()
                .transform(Unchecked.function(refreshToken -> {
                    if (refreshToken.getDataHoraExpiracao().isBefore(LocalDateTime.now())) {
                        throw new BusinessException(ArquiteturaErrorCodeEnum.TOKEN_INVALIDO, "Refresh token expirado");
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
