package br.com.familyfinance.autenticador.domain.exception;

import br.com.familyfinance.arquitetura.domain.exception.BusinessException;

public class CredenciasNaoInformadasException extends BusinessException {
    public CredenciasNaoInformadasException() {
        super(AutenticadorErrorCodeEnum.CREDENCIAIS_INVALIDAS, "Credenciais não informadas");
    }
}
