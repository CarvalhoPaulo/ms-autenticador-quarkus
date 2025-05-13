package br.com.familyfinance.autenticador.domain.exception;

import br.dev.paulocarvalho.arquitetura.domain.exception.BusinessException;

public class InvalidEmailException extends BusinessException {
    public InvalidEmailException() {
        super(AutenticadorErrorCodeEnum.INVALID_CREDENTIALS, "Email inválido");
    }
}
