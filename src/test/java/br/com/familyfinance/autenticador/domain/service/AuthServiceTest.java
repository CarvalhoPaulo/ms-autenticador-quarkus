package br.com.familyfinance.autenticador.domain.service;

import br.com.familyfinance.autenticador.application.dto.LoginDTO;
import br.com.familyfinance.autenticador.application.dto.TokenDTO;
import br.com.familyfinance.autenticador.domain.service.impl.AuthServiceImpl;
import br.com.familyfinance.autenticador.shared.utils.JwtUtil;
import br.dev.paulocarvalho.arquitetura.domain.exception.BusinessException;
import br.dev.paulocarvalho.autenticador.domain.model.RefreshToken;
import br.dev.paulocarvalho.autenticador.domain.model.User;
import io.smallrye.jwt.build.Jwt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(jwtUtil.generateToken(any()))
                .thenReturn(Jwt.issuer("http://issuer.com")
                        .subject("1")
                        .upn("usuario@teste.com")
                        .preferredUserName("usuario@teste.com")
                        .expiresIn(Duration.ofSeconds(30))
                        .issuedAt(Instant.now())
                        .sign());
    }

    @Test
    void deveAutenticarUserComSucesso() throws BusinessException {
        // Arrange
        String email = "user.teste@email.com";
        String username = "user.teste";
        String senha = "Senha@123";
        String name = "Nome Completo";

        User user = User.builder()
                .id(1L)
                .name(name)
                .email(email)
                .username(username)
                .password(senha)
                .build();

        RefreshToken refreshToken = RefreshToken.create(user);

        when(userService.findByUsername(username))
                .thenReturn(user);
        when(refreshTokenService.createRefreshToken(user))
                .thenReturn(refreshToken);

        // Act
        TokenDTO resultado = authService.login(LoginDTO.builder()
                .username(username)
                .password(senha)
                .build());

        // Assert
        assertNotNull(resultado);
        assertNotNull(resultado.getAccessToken());
        assertNotNull(resultado.getRefreshToken());

        verify(userService).findByUsername(username);
        verify(refreshTokenService).createRefreshToken(user);
    }
}
