package br.com.familyfinance.autenticador.core.exception;

import br.com.familyfinance.arquitetura.core.exception.ErrorCodeEnum;
import jakarta.ws.rs.core.Response;
import lombok.Getter;

@Getter
public enum AutenticadorErrorCodeEnum implements ErrorCodeEnum {

    CREDENCIAIS_INVALIDA("ATT0001", "Usuário e/ou senha inválidos.");

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
