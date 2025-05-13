package br.com.familyfinance.autenticador.domain.exception;

import br.dev.paulocarvalho.arquitetura.domain.exception.BusinessException;

public class InvalidProfileNameException extends BusinessException {
    public InvalidProfileNameException() {
        super(AutenticadorErrorCodeEnum.INVALID_PROFILE_NAME);
    }
}
