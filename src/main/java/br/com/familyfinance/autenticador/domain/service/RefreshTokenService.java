package br.com.familyfinance.autenticador.domain.service;

import br.dev.paulocarvalho.arquitetura.application.exception.ApplicationException;
import br.dev.paulocarvalho.arquitetura.domain.exception.BusinessException;
import br.dev.paulocarvalho.autenticador.domain.model.RefreshToken;
import br.dev.paulocarvalho.autenticador.domain.model.User;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(User user) throws BusinessException;

    RefreshToken updateRefreshToken(String refreshToken) throws ApplicationException, BusinessException;
}
