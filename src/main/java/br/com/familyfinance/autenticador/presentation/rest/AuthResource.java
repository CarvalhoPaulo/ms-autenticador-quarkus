package br.com.familyfinance.autenticador.presentation.rest;

import br.com.familyfinance.autenticador.application.dto.LoginDTO;
import br.com.familyfinance.autenticador.application.dto.TokenDTO;
import br.com.familyfinance.autenticador.domain.service.AuthService;
import br.dev.paulocarvalho.arquitetura.application.dto.MetaDTO;
import br.dev.paulocarvalho.arquitetura.application.dto.ResponseDTO;
import br.dev.paulocarvalho.arquitetura.application.exception.ApplicationErrorCodeEnum;
import br.dev.paulocarvalho.arquitetura.application.exception.ApplicationException;
import br.dev.paulocarvalho.arquitetura.domain.exception.BusinessException;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.Cookie;
import io.vertx.ext.web.RoutingContext;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
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
    @WithTransaction
    @Operation(summary = "Login do usuário", description = "Recebe credenciais e retorna um token de acesso JWT")
    public Uni<ResponseDTO<TokenDTO, MetaDTO>> login(
            @RequestBody(
                    description = "Credenciais do usuário",
                    required = false
            )
            LoginDTO loginDTO,
            @Context
            RoutingContext ctx) {
        return authService.login(loginDTO)
                .onItem()
                .transform(tokenDTO -> adicionarRefreshTokenCookie(ctx, tokenDTO))
                .onItem()
                .transform(tokenDto -> ResponseDTO.<TokenDTO, MetaDTO>builder()
                        .data(tokenDto)
                        .meta(MetaDTO.of(LocalDateTime.now()))
                        .build())
                .onFailure(ex -> !(ex.getCause() instanceof BusinessException))
                .transform(ex -> new ApplicationException(ApplicationErrorCodeEnum.ERRO_INTERNO, ex));
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
    @WithTransaction
    public Uni<ResponseDTO<TokenDTO, MetaDTO>> refreshToken(
            @CookieParam("refreshToken") String refreshToken,
            @Context RoutingContext ctx) {
        return authService.refreshToken(refreshToken)
                .onItem()
                .transform(tokenDTO -> adicionarRefreshTokenCookie(ctx, tokenDTO))
                .onItem()
                .transform(tokenDTO -> ResponseDTO.<TokenDTO, MetaDTO>builder()
                        .meta(MetaDTO.of(LocalDateTime.now()))
                        .data(tokenDTO)
                        .build());
    }

    private TokenDTO adicionarRefreshTokenCookie(RoutingContext ctx, TokenDTO tokenDTO) {
        ctx.response().addCookie(Cookie.cookie("refreshToken", tokenDTO.getRefreshToken().getToken())
                .setPath("/")
                .setHttpOnly(true)
                .setMaxAge(tokenDTO.getRefreshToken().getTempoMaximoDuracaoSegundos()));
        return tokenDTO;
    }

}
