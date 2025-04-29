package br.com.familyfinance.autenticador.domain.exception;

import br.com.familyfinance.arquitetura.domain.exception.ArquiteturaErrorCodeEnum;
import br.com.familyfinance.arquitetura.domain.exception.BusinessException;

public class RefreshTokenExpiradoException extends BusinessException {
    public RefreshTokenExpiradoException() {
        super(ArquiteturaErrorCodeEnum.TOKEN_INVALIDO, "Refresh token expirado");
    }
}
