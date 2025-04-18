package br.com.familyfinance.autenticador.infra.dto;

import br.com.familyfinance.arquitetura.core.dto.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Schema(description = "DTO que contém o token de acesso")
public class TokenDTO extends DTO {

    @Schema(
            description = "O token de acesso gerado após o login do usuário",
            required = true
    )
    private String accessToken;
}
