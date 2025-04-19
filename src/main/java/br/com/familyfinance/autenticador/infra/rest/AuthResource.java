package br.com.familyfinance.autenticador.infra.rest;

import br.com.familyfinance.arquitetura.core.dto.MetaDTO;
import br.com.familyfinance.arquitetura.core.dto.ResponseDTO;
import br.com.familyfinance.arquitetura.core.exception.ArquiteturaErrorCodeEnum;
import br.com.familyfinance.arquitetura.core.exception.BusinessException;
import br.com.familyfinance.autenticador.core.exception.AutenticadorErrorCodeEnum;
import br.com.familyfinance.autenticador.core.model.RefreshToken;
import br.com.familyfinance.autenticador.core.model.Usuario;
import br.com.familyfinance.autenticador.core.service.RefreshTokenService;
import br.com.familyfinance.autenticador.core.service.UsuarioService;
import br.com.familyfinance.autenticador.infra.dto.LoginDTO;
import br.com.familyfinance.autenticador.infra.dto.TokenDTO;
import br.com.familyfinance.autenticador.infra.utils.JwtUtil;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
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

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

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
                    required = false
            )
            LoginDTO loginDTO,
            @HeaderParam("Authorization") String authorization,
            @Context
            RoutingContext ctx) {
        LoginDTO finalLoginDTO = getLoginDTO(loginDTO, authorization);

        if (finalLoginDTO == null || finalLoginDTO.getEmail() == null || finalLoginDTO.getSenha() == null) {
            return Uni.createFrom().failure(new BusinessException(AutenticadorErrorCodeEnum.CREDENCIAIS_INVALIDA));
        }

        return usuarioService.buscarPorEmail(finalLoginDTO.getEmail())
                .onItem()
                .transform(Unchecked.function(usuario -> validarUsuario(finalLoginDTO, usuario)))
                .onItem()
                .transformToUni(usuario -> adicionarRefreshTokenCookie(ctx, usuario))
                .onItem()
                .transform(this::gerarTokenResposta)
                .onFailure(ex -> !(ex.getCause() instanceof BusinessException))
                .transform(ex -> new BusinessException(ArquiteturaErrorCodeEnum.ERRO_INTERNO, ex));
        // Qualquer erro que não for BusinessException será convertido para um;
    }

    private static LoginDTO getLoginDTO(LoginDTO loginDTO, String authorization) {
        LoginDTO finalLoginDTO;
        if (loginDTO == null && authorization != null && authorization.startsWith("Basic ")) {
            String token = authorization.replaceFirst("Basic ", "").trim();
            // Decodifica de Base64
            byte[] decodedBytes = Base64.getDecoder().decode(token);
            String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);

            // Divide em usuário e senha
            String[] parts = decodedString.split(":", 2);
            String email = parts[0];
            String senha = parts.length > 1 ? parts[1] : "";

            finalLoginDTO = new LoginDTO();
            finalLoginDTO.setEmail(email);
            finalLoginDTO.setSenha(senha);
        } else if (loginDTO != null && loginDTO.getEmail() != null && loginDTO.getSenha() != null) {
            finalLoginDTO = loginDTO;
        } else {
            finalLoginDTO = null;
        }
        return finalLoginDTO;
    }

    private Usuario validarUsuario(LoginDTO loginDTO, Usuario usuario) throws BusinessException {
        if (usuario == null || !BcryptUtil.matches(loginDTO.getSenha(), usuario.getSenha())) {
            throw new BusinessException(AutenticadorErrorCodeEnum.CREDENCIAIS_INVALIDA);
        }

        return usuario;
    }

    private RefreshToken adicionarRefreshTokenCookie(RoutingContext ctx, RefreshToken refreshToken) {
        ctx.response().addCookie(Cookie.cookie("refreshToken", refreshToken.getToken())
                .setPath("/")
                .setHttpOnly(true)
                .setMaxAge(refreshToken.getTempoMaximoDuracaoSegundos()));
        return refreshToken;
    }

    private Uni<Usuario> adicionarRefreshTokenCookie(RoutingContext ctx, Usuario usuario) {
        return refreshTokenService.createRefreshToken(usuario)
                .onItem()
                .transform(refreshToken -> {
                    adicionarRefreshTokenCookie(ctx, refreshToken);
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
        return refreshTokenService.updateRefreshToken(refreshToken)
                .onItem()
                .transform(newRefreshToken -> adicionarRefreshTokenCookie(ctx, newRefreshToken))
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
