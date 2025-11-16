package br.com.familyfinance.autenticador.domain.service.impl;

import br.com.familyfinance.autenticador.application.dto.LoginDTO;
import br.com.familyfinance.autenticador.application.dto.TokenDTO;
import br.com.familyfinance.autenticador.domain.service.AuthService;
import br.com.familyfinance.autenticador.domain.service.RefreshTokenService;
import br.com.familyfinance.autenticador.domain.service.UserService;
import br.com.familyfinance.autenticador.shared.utils.JwtUtil;
import br.dev.paulocarvalho.arquitetura.application.exception.ApplicationException;
import br.dev.paulocarvalho.arquitetura.domain.exception.BusinessException;
import br.dev.paulocarvalho.autenticador.domain.exception.CredentialsNotFoundException;
import br.dev.paulocarvalho.autenticador.domain.model.RefreshToken;
import br.dev.paulocarvalho.autenticador.domain.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AuthServiceImpl implements AuthService {

    @Inject
    UserService userService;

    @Inject
    JwtUtil jwtUtil;

    @Inject
    RefreshTokenService refreshTokenService;

    @Override
    public TokenDTO login(LoginDTO loginDTO) throws BusinessException {
        if (loginDTO == null || loginDTO.getUsername() == null || loginDTO.getPassword() == null) {
            throw new CredentialsNotFoundException();
        }

        User user = userService.findByUsername(loginDTO.getUsername());
        user.checkPassword(loginDTO.getPassword());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        return gerarTokenResposta(refreshToken);
    }

    @Override
    public TokenDTO refreshToken(String refreshToken) throws BusinessException, ApplicationException {
        RefreshToken newRefreshToken = refreshTokenService.updateRefreshToken(refreshToken);
        return TokenDTO.builder()
                .accessToken(jwtUtil.generateToken(newRefreshToken.getUser()))
                .refreshToken(newRefreshToken)
                .build();
    }

    private TokenDTO gerarTokenResposta(RefreshToken refreshToken) {
        String token = jwtUtil.generateToken(refreshToken.getUser());
        return TokenDTO.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }
}
