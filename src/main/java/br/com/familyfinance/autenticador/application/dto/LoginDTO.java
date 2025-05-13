package br.com.familyfinance.autenticador.application.dto;

import lombok.Builder;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(description = "Objeto com as credenciais de login do usuário")
public class LoginDTO {
    @Schema(
            description = "Nome de usuário",
            required = true
    )
    @NotBlank
    private String username;

    @Schema(
            description = "Senha do usuário",
            required = true
    )
    @NotBlank
    private String password;
}
