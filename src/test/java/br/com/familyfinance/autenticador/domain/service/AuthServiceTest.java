package br.com.familyfinance.autenticador.domain.service;

import br.com.familyfinance.autenticador.application.dto.LoginDTO;
import br.com.familyfinance.autenticador.application.dto.TokenDTO;
import br.com.familyfinance.autenticador.domain.exception.InvalidEmailException;
import br.com.familyfinance.autenticador.domain.exception.InvalidFullNameException;
import br.com.familyfinance.autenticador.domain.exception.InvalidPasswordlException;
import br.com.familyfinance.autenticador.domain.exception.InvalidUsernameException;
import br.com.familyfinance.autenticador.domain.model.RefreshToken;
import br.com.familyfinance.autenticador.domain.model.User;
import br.com.familyfinance.autenticador.domain.service.impl.AuthServiceImpl;
import br.com.familyfinance.autenticador.shared.utils.JwtUtil;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.mutiny.Uni;
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
    private UsuarioService usuarioService;

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
    void deveAutenticarUsuarioComSucesso() throws InvalidPasswordlException, InvalidUsernameException, InvalidFullNameException, InvalidEmailException {
        // Arrange
        String email = "usuario.teste@email.com";
        String username = "usuario.teste";
        String senha = "Senha@123";
        String name = "Nome Completo";

        User user = User.builder()
                .id(1L)
                .name(name)
                .email(email)
                .username(username)
                .plainPassword(senha)
                .build();

        RefreshToken refreshToken = RefreshToken.create(user);

        when(usuarioService.findByUsername(username))
                .thenReturn(Uni.createFrom().item(user));
        when(refreshTokenService.createRefreshToken(user))
                .thenReturn(Uni.createFrom().item(refreshToken));

        // Act
        Uni<TokenDTO> resultado = authService.login(LoginDTO.builder()
                .username(username)
                .password(senha)
                .build());

        // Assert
        resultado.subscribe().with(response -> {
            assertNotNull(response);
            assertNotNull(response.getAccessToken());
            assertNotNull(response.getRefreshToken());
        });

        verify(usuarioService).findByUsername(username);
        verify(refreshTokenService).createRefreshToken(user);
    }
}
