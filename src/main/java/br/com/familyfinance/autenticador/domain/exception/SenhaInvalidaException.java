package br.com.familyfinance.autenticador.domain.exception;

import br.dev.paulocarvalho.arquitetura.domain.exception.BusinessException;

public class SenhaInvalidaException extends BusinessException {
    public SenhaInvalidaException() {
        super(AutenticadorErrorCodeEnum.INVALID_CREDENTIALS, "Senha inválida");
    }
}
