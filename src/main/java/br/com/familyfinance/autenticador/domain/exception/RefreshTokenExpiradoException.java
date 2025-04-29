package br.com.familyfinance.autenticador.domain.exception;

import br.com.familyfinance.arquitetura.domain.exception.BusinessErrorCodeEnum;
import br.com.familyfinance.arquitetura.domain.exception.BusinessException;

public class RefreshTokenExpiradoException extends BusinessException {
    public RefreshTokenExpiradoException() {
        super(BusinessErrorCodeEnum.TOKEN_INVALIDO, "Refresh token expirado");
    }
}
