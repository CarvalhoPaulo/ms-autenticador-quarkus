package br.com.familyfinance.autenticador.application.dto;

import lombok.Builder;
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
            description = "Email do usuário",
            example = "usuario@email.com",
            required = true
    )
    @NotBlank
    private String email;

    @Schema(
            description = "Senha do usuário",
            example = "123456",
            required = true
    )
    @NotBlank
    private String senha;
}
