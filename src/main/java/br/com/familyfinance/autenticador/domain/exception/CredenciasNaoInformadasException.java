package br.com.familyfinance.autenticador.domain.exception;

import br.dev.paulocarvalho.arquitetura.domain.exception.BusinessException;

public class CredenciasNaoInformadasException extends BusinessException {
    public CredenciasNaoInformadasException() {
        super(AutenticadorErrorCodeEnum.INVALID_CREDENTIALS, "Credenciais não informadas");
    }
}
