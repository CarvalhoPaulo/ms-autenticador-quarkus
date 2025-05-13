package br.com.familyfinance.autenticador.domain.exception;

import br.dev.paulocarvalho.arquitetura.domain.exception.BusinessErrorCode;
import jakarta.ws.rs.core.Response;
import lombok.Getter;

@Getter
public enum AutenticadorErrorCodeEnum implements BusinessErrorCode {

    INVALID_CREDENTIALS("ATT0001", "Usuário e/ou senha inválidos."),
    INVALID_EMAIL("ATT0002", "Email inválido."),
    INVALID_PROFILE_NAME("ATT0003", "Nome do perfil inválido."),
    INVALID_PROFILE_FEATURES("ATT0004", "Funcionalidades do perfil não informadas."),
    INVALID_PASSWORD("ATT0005", "Senha inválida. A senha deve ter no mínimo 8 caracteres, " +
            "incluindo letras maiúscula e minúscula, números e caracteres especiais (como !@#$%^&*)."),
    INVALID_USERNAME("ATT0006", "Nome de usuário invalido. "),
    INVALID_FULL_NAME("ATT0007", "Nome completo inválido. Precisa ter ao menos um nome e sobrenome.");

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
