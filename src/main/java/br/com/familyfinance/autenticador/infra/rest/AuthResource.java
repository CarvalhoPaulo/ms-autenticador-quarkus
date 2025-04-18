package br.com.familyfinance.autenticador.infra.rest;

import br.com.familyfinance.arquitetura.core.dto.MetaDTO;
import br.com.familyfinance.arquitetura.core.dto.ResponseDTO;
import br.com.familyfinance.arquitetura.core.exception.ArquiteturaErrorCodeEnum;
import br.com.familyfinance.arquitetura.core.exception.BusinessException;
import br.com.familyfinance.autenticador.core.exception.AutenticadorErrorCodeEnum;
import br.com.familyfinance.autenticador.core.model.Usuario;
import br.com.familyfinance.autenticador.core.service.RefreshTokenService;
import br.com.familyfinance.autenticador.core.service.UsuarioService;
import br.com.familyfinance.autenticador.infra.dto.LoginDTO;
import br.com.familyfinance.autenticador.infra.dto.TokenDTO;
import br.com.familyfinance.autenticador.infra.utils.JwtUtil;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import io.vertx.core.http.Cookie;
import io.vertx.ext.web.RoutingContext;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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
    UsuarioService usuarioService;

    @Inject
    JwtUtil jwtUtil;

    @Inject
    RefreshTokenService refreshTokenService;

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
                    required = true
            )
            LoginDTO loginDTO,
            @Context
            RoutingContext ctx) {
        return usuarioService.buscarPorEmail(loginDTO.getEmail())
                .onItem()
                .transform(Unchecked.function(usuario -> validarUsuario(loginDTO, usuario)))
                .onItem()
                .transformToUni(usuario -> adicionarRefreshTokenCookie(ctx, usuario))
                .onItem()
                .transform(this::gerarTokenResposta)
                .onFailure(ex -> !(ex.getCause() instanceof BusinessException))
                .transform(ex -> new BusinessException(ArquiteturaErrorCodeEnum.ERRO_INTERNO, ex));
        // Qualquer erro que não for BusinessException será convertido para um;
    }

    private Usuario validarUsuario(LoginDTO loginDTO, Usuario usuario) throws BusinessException {
        if (usuario == null || !BcryptUtil.matches(loginDTO.getSenha(), usuario.getSenha())) {
            throw new BusinessException(AutenticadorErrorCodeEnum.CREDENCIAIS_INVALIDA);
        }

        return usuario;
    }

    private Uni<Usuario> adicionarRefreshTokenCookie(RoutingContext ctx, Usuario usuario) {
        return refreshTokenService.createRefreshToken(usuario)
                .onItem()
                .transform(refreshToken -> {
                    ctx.response().addCookie(Cookie.cookie("refreshToken", refreshToken.getToken())
                            .setPath("/")
                            .setHttpOnly(true)
                            .setMaxAge(refreshToken.getTempoMaximoDuracaoSegundos()));
                    return usuario;
                });
    }

    private ResponseDTO<TokenDTO, MetaDTO> gerarTokenResposta(Usuario usuario) {
        String token = jwtUtil.generateToken(usuario.getEmail());
        return ResponseDTO.<TokenDTO, MetaDTO>builder()
                .meta(MetaDTO.of(LocalDateTime.now()))
                .data(TokenDTO.builder()
                        .accessToken(token)
                        .build())
                .build();
    }

    @POST
    @Path("/refresh-token")
    @Transactional
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
    public Uni<ResponseDTO<TokenDTO, MetaDTO>> refreshToken(@CookieParam("refreshToken") String refreshToken) {
        return refreshTokenService.updateRefreshToken(refreshToken)
                .onItem()
                .transform(newRefreshToken -> {
                    String newToken = jwtUtil.generateToken(newRefreshToken
                            .getUsuario()
                            .getNome());
                    return ResponseDTO.<TokenDTO, MetaDTO>builder()
                            .meta(MetaDTO.of(LocalDateTime.now()))
                            .data(TokenDTO.builder()
                                    .accessToken(newToken)
                                    .build())
                            .build();
                });
    }


}
