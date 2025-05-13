package br.com.familyfinance.autenticador.domain.exception;

import br.dev.paulocarvalho.arquitetura.domain.exception.BusinessException;

public class UserNotRegistredException extends BusinessException {
    public UserNotRegistredException() {
        super(AutenticadorErrorCodeEnum.INVALID_CREDENTIALS, "Usuário não encontrado");
    }
}
