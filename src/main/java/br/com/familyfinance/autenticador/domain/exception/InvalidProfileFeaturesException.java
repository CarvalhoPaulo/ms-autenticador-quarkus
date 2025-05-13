package br.com.familyfinance.autenticador.domain.exception;

import br.dev.paulocarvalho.arquitetura.domain.exception.BusinessException;

public class InvalidProfileFeaturesException extends BusinessException {
    public InvalidProfileFeaturesException() {
        super(AutenticadorErrorCodeEnum.INVALID_PROFILE_FEATURES);
    }
}
