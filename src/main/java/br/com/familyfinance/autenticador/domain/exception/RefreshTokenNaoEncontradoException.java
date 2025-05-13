package br.com.familyfinance.autenticador.domain.exception;

import br.dev.paulocarvalho.arquitetura.domain.exception.BusinessErrorCodeEnum;
import br.dev.paulocarvalho.arquitetura.domain.exception.BusinessException;

public class RefreshTokenNaoEncontradoException extends BusinessException {
    public RefreshTokenNaoEncontradoException() {
        super(BusinessErrorCodeEnum.TOKEN_INVALIDO, "Refresh token não encontrado");
    }
}
