package br.com.familyfinance.autenticador.domain.service;

import br.com.familyfinance.autenticador.application.dto.LoginDTO;
import br.com.familyfinance.autenticador.application.dto.TokenDTO;
import br.dev.paulocarvalho.arquitetura.application.exception.ApplicationException;
import br.dev.paulocarvalho.arquitetura.domain.exception.BusinessException;
import br.dev.paulocarvalho.autenticador.domain.exception.CredentialsNotFoundException;
import br.dev.paulocarvalho.autenticador.domain.exception.PasswordMatchesException;

public interface AuthService {
    TokenDTO login(LoginDTO loginDTO) throws BusinessException;

    TokenDTO refreshToken(String refreshToken) throws BusinessException, ApplicationException;
}
