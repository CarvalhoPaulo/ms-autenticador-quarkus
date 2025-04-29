package br.com.familyfinance.autenticador.domain.exception;

import br.com.familyfinance.arquitetura.domain.exception.BusinessErrorCode;
import jakarta.ws.rs.core.Response;
import lombok.Getter;

@Getter
public enum AutenticadorErrorCodeEnum implements BusinessErrorCode {

    CREDENCIAIS_INVALIDAS("ATT0001", "Usuário e/ou senha inválidos.");

    private final String codigo;
    private final String message;
    private final int httpStatus;

    AutenticadorErrorCodeEnum(String codigo, String message, int httpStatus) {
        this.message = message;
        this.codigo = codigo;
        this.httpStatus = httpStatus;
    }

    AutenticadorErrorCodeEnum(String codigo, String message) {
        this.message = message;
        this.codigo = codigo;
        this.httpStatus = Response.Status.BAD_REQUEST.getStatusCode();
    }
}
