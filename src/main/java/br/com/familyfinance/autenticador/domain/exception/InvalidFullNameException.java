package br.com.familyfinance.autenticador.domain.exception;

import br.dev.paulocarvalho.arquitetura.domain.exception.BusinessException;

public class InvalidFullNameException extends BusinessException {
    public InvalidFullNameException() {
        super(AutenticadorErrorCodeEnum.INVALID_FULL_NAME);
    }
}
