package br.com.familyfinance.autenticador.domain.exception;

import br.dev.paulocarvalho.arquitetura.domain.exception.BusinessException;

public class InvalidPasswordlException extends BusinessException {
    public InvalidPasswordlException() {
        super(AutenticadorErrorCodeEnum.INVALID_PASSWORD);
    }
}
