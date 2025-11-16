package br.com.familyfinance.autenticador.domain.service.impl;

import br.com.familyfinance.autenticador.domain.repository.RefreshTokenRepository;
import br.com.familyfinance.autenticador.domain.service.RefreshTokenService;
import br.dev.paulocarvalho.arquitetura.application.exception.ApplicationException;
import br.dev.paulocarvalho.arquitetura.domain.exception.BusinessException;
import br.dev.paulocarvalho.autenticador.domain.exception.RefreshTokenExpiradoException;
import br.dev.paulocarvalho.autenticador.domain.exception.RefreshTokenNaoEncontradoException;
import br.dev.paulocarvalho.autenticador.domain.model.RefreshToken;
import br.dev.paulocarvalho.autenticador.domain.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;

@ApplicationScoped
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Inject
    RefreshTokenRepository repository;

    @Override
    public RefreshToken createRefreshToken(User user) throws BusinessException {
        return repository.create(getNovoRefreshToken(user));
    }

    @Override
    @Transactional
    public RefreshToken updateRefreshToken(String token) throws BusinessException, ApplicationException {
        RefreshToken refreshToken = repository.findByToken(token)
                .orElseThrow(RefreshTokenNaoEncontradoException::new);

        if (refreshToken.getExpirationDateTime().isBefore(LocalDateTime.now())) {
            throw new RefreshTokenExpiradoException();
        }

        refreshToken.renovarToken();
        return repository.update(refreshToken);
    }

    private static RefreshToken getNovoRefreshToken(User user) {
        return RefreshToken.create(user);
    }
}
