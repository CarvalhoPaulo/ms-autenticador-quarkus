package br.com.familyfinance.autenticador.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Getter
@Setter
@Builder
@Schema(description = "Objeto com as credenciais de login do usuário")
public class LoginDTO {
    @Schema(
            description = "Nome de usuário",
            required = true,
            examples = {"user.name"}
    )
    @NotBlank
    private String username;

    @Schema(
            description = "Senha do usuário",
            required = true,
            examples = {"passwordXPTO"}
    )
    @NotBlank
    private String password;
}
