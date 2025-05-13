package br.com.familyfinance.autenticador.domain.service.impl;

import br.com.familyfinance.autenticador.application.dto.LoginDTO;
import br.com.familyfinance.autenticador.application.dto.TokenDTO;
import br.com.familyfinance.autenticador.domain.exception.CredenciasNaoInformadasException;
import br.com.familyfinance.autenticador.domain.model.RefreshToken;
import br.com.familyfinance.autenticador.domain.service.AuthService;
import br.com.familyfinance.autenticador.domain.service.RefreshTokenService;
import br.com.familyfinance.autenticador.domain.service.UsuarioService;
import br.com.familyfinance.autenticador.shared.utils.JwtUtil;
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
        if (loginDTO == null || loginDTO.getUsername() == null || loginDTO.getPassword() == null) {
            return Uni.createFrom().failure(new CredenciasNaoInformadasException());
        }

        return usuarioService.findByUsername(loginDTO.getUsername())
                .onItem()
                .transform(Unchecked.function(user -> user.checkPassword(loginDTO.getPassword())))
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
                        .accessToken(jwtUtil.generateToken(newRefreshToken.getUser()))
                        .refreshToken(newRefreshToken)
                        .build());
    }

    private TokenDTO gerarTokenResposta(RefreshToken refreshToken) {
        String token = jwtUtil.generateToken(refreshToken.getUser());
        return TokenDTO.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }
}
