package br.com.familyfinance.autenticador.application.dto;

import br.dev.paulocarvalho.autenticador.domain.model.RefreshToken;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import br.dev.paulocarvalho.arquitetura.application.dto.DTO;

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
    @JsonIgnore
    private RefreshToken refreshToken;
}
