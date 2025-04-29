package br.com.familyfinance.autenticador.domain.exception;

import br.com.familyfinance.arquitetura.domain.exception.ArquiteturaErrorCodeEnum;
import br.com.familyfinance.arquitetura.domain.exception.BusinessException;

public class RefreshTokenNaoEncontradoException extends BusinessException {
    public RefreshTokenNaoEncontradoException() {
        super(ArquiteturaErrorCodeEnum.TOKEN_INVALIDO, "Refresh token não encontrado");
    }
}
