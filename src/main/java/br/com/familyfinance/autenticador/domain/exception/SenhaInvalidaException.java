package br.com.familyfinance.autenticador.domain.exception;

import br.com.familyfinance.arquitetura.domain.exception.BusinessException;

public class SenhaInvalidaException extends BusinessException {
    public SenhaInvalidaException() {
        super(AutenticadorErrorCodeEnum.CREDENCIAIS_INVALIDAS, "Senha inválida");
    }
}
