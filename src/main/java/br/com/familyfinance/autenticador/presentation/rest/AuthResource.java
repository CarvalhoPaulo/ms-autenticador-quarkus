package br.com.familyfinance.autenticador.presentation.rest;

import br.com.familyfinance.autenticador.application.dto.LoginDTO;
import br.com.familyfinance.autenticador.application.dto.TokenDTO;
import br.com.familyfinance.autenticador.domain.service.AuthService;
import br.dev.paulocarvalho.arquitetura.application.dto.MetaDTO;
import br.dev.paulocarvalho.arquitetura.application.dto.ResponseDTO;
import br.dev.paulocarvalho.arquitetura.application.exception.ApplicationErrorCodeEnum;
import br.dev.paulocarvalho.arquitetura.application.exception.ApplicationException;
import br.dev.paulocarvalho.arquitetura.domain.exception.BusinessException;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.time.LocalDateTime;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Autenticação", description = "Endpoints de login e refresh")
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    @APIResponse(
            responseCode = "200",
            description = "Token de acesso gerado com sucesso"
    )
    @APIResponse(
            responseCode = "401",
            description = "Credenciais inválidas"
    )
    @Transactional
    @Operation(summary = "Login do usuário", description = "Recebe credenciais e retorna um token de acesso JWT")
    public Response login(
            @RequestBody(
                    description = "Credenciais do usuário",
                    required = false
            )
            LoginDTO loginDTO) throws ApplicationException, BusinessException {
        try {
            TokenDTO tokenDTO = authService.login(loginDTO);
            ResponseDTO<TokenDTO, MetaDTO> responseDTO = ResponseDTO.<TokenDTO, MetaDTO>builder()
                    .data(tokenDTO)
                    .meta(MetaDTO.of(LocalDateTime.now()))
                    .build();
            NewCookie refreshTokenCookie = buildRefreshTokenCookie(tokenDTO);
            return Response.ok(responseDTO).cookie(refreshTokenCookie).build();
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException(ApplicationErrorCodeEnum.ERRO_INTERNO, e);
        }
    }

    @POST
    @Path("/refresh")
    @Operation(summary = "Atualização do token de acesso", description = "Utiliza o refreshToken da sessão para " +
            "gerar um novo token de acesso")
    @APIResponse(
            responseCode = "200",
            description = "Token de acesso gerado com sucesso"
    )
    @APIResponse(
            responseCode = "401",
            description = "Credenciais inválidas"
    )
    @Transactional
    public Response refreshToken(@CookieParam("refreshToken") String refreshToken) throws BusinessException, ApplicationException {
        TokenDTO tokenDTO = authService.refreshToken(refreshToken);
        ResponseDTO<TokenDTO, MetaDTO> responseDTO = ResponseDTO.<TokenDTO, MetaDTO>builder()
                .meta(MetaDTO.of(LocalDateTime.now()))
                .data(tokenDTO)
                .build();
        NewCookie newRefreshTokenCookie = buildRefreshTokenCookie(tokenDTO);
        return Response.ok(responseDTO).cookie(newRefreshTokenCookie).build();
    }

    private NewCookie buildRefreshTokenCookie(TokenDTO tokenDTO) {
        return new NewCookie.Builder("refreshToken")
                .value(tokenDTO.getRefreshToken().getToken())
                .path("/")
                .httpOnly(true)
                .maxAge((int) tokenDTO.getRefreshToken().getTempoMaximoDuracaoSegundos())
                .build();
    }

}
