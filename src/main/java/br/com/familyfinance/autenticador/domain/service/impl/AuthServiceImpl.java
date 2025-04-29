package br.com.familyfinance.autenticador.domain.service.impl;

import br.com.familyfinance.arquitetura.domain.exception.BusinessException;
import br.com.familyfinance.autenticador.domain.exception.AutenticadorErrorCodeEnum;
import br.com.familyfinance.autenticador.domain.exception.CredenciasNaoInformadasException;
import br.com.familyfinance.autenticador.domain.exception.SenhaInvalidaException;
import br.com.familyfinance.autenticador.domain.exception.UsuarioNaoCadastradoException;
import br.com.familyfinance.autenticador.domain.model.RefreshToken;
import br.com.familyfinance.autenticador.domain.model.Usuario;
import br.com.familyfinance.autenticador.domain.service.RefreshTokenService;
import br.com.familyfinance.autenticador.domain.service.UsuarioService;
import br.com.familyfinance.autenticador.application.dto.LoginDTO;
import br.com.familyfinance.autenticador.application.dto.TokenDTO;
import br.com.familyfinance.autenticador.domain.service.AuthService;
import br.com.familyfinance.autenticador.shared.utils.JwtUtil;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AuthServiceImpl implements AuthService {

    @Inject
    UsuarioService usuarioService;

    @Inject
    JwtUtil jwtUtil;

    @Inject
    RefreshTokenService refreshTokenService;

    @Override
    public Uni<TokenDTO> login(LoginDTO loginDTO) {
        if (loginDTO == null || loginDTO.getEmail() == null || loginDTO.getSenha() == null) {
            return Uni.createFrom().failure(new CredenciasNaoInformadasException());
        }

        return usuarioService.buscarPorEmail(loginDTO.getEmail())
                .onItem()
                .transform(Unchecked.function(usuario -> validarUsuario(loginDTO, usuario)))
                .onItem()
                .transformToUni(usuario -> refreshTokenService.createRefreshToken(usuario))
                .onItem()
                .transform(this::gerarTokenResposta);
    }

    @Override
    public Uni<TokenDTO> refreshToken(String refreshToken) {
        return refreshTokenService.updateRefreshToken(refreshToken)
                .onItem()
                .transform(newRefreshToken -> TokenDTO.builder()
                        .accessToken(jwtUtil.generateToken(newRefreshToken.getUsuario()))
                        .refreshToken(newRefreshToken)
                        .build());
    }

    private Usuario validarUsuario(LoginDTO loginDTO, Usuario usuario) throws BusinessException {
        if (!BcryptUtil.matches(loginDTO.getSenha(), usuario.getSenha())) {
            throw new SenhaInvalidaException();
        }

        return usuario;
    }

    private TokenDTO gerarTokenResposta(RefreshToken refreshToken) {
        String token = jwtUtil.generateToken(refreshToken.getUsuario());
        return TokenDTO.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }
}
