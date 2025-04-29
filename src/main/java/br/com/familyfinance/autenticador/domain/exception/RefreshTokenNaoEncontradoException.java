package br.com.familyfinance.autenticador.domain.exception;

import br.com.familyfinance.arquitetura.domain.exception.BusinessErrorCodeEnum;
import br.com.familyfinance.arquitetura.domain.exception.BusinessException;

public class RefreshTokenNaoEncontradoException extends BusinessException {
    public RefreshTokenNaoEncontradoException() {
        super(BusinessErrorCodeEnum.TOKEN_INVALIDO, "Refresh token não encontrado");
    }
}
