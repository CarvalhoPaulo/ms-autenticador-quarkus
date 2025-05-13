package br.com.familyfinance.autenticador.domain.exception;

import br.dev.paulocarvalho.arquitetura.domain.exception.BusinessException;

public class InvalidUsernameException extends BusinessException {
    public InvalidUsernameException() {
        super(AutenticadorErrorCodeEnum.INVALID_USERNAME);
    }
}
