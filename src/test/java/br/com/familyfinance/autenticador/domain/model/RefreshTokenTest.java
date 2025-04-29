package br.com.familyfinance.autenticador.domain.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class RefreshTokenTest {

    @Test
    void deveCriarRefreshTokenComSucesso() {
        // Arrange
        Usuario usuario = Usuario.builder()
                .id(1L)
                .nome("João Silva")
                .email("joao@email.com")
                .senha("senha123")
                .build();

        // Act
        RefreshToken refreshToken = RefreshToken.create(usuario);

        // Assert
        assertNotNull(refreshToken);
        assertNotNull(refreshToken.getToken());
        assertEquals(usuario, refreshToken.getUsuario());
        assertNotNull(refreshToken.getDataHoraCriacao());
        assertNotNull(refreshToken.getDataHoraExpiracao());
        assertTrue(refreshToken.getDataHoraExpiracao().isAfter(refreshToken.getDataHoraCriacao()));
    }

    @Test
    void deveRenovarTokenComSucesso() {
        // Arrange
        Usuario usuario = Usuario.builder()
                .id(1L)
                .nome("João Silva")
                .build();
        
        RefreshToken refreshToken = RefreshToken.create(usuario);
        String tokenAntigo = refreshToken.getToken();
        LocalDateTime dataExpiracaoAntiga = refreshToken.getDataHoraExpiracao();

        // Act
        refreshToken.renovarToken();

        // Assert
        assertNotEquals(tokenAntigo, refreshToken.getToken());
        assertTrue(refreshToken.getDataHoraExpiracao().isAfter(dataExpiracaoAntiga));
    }

    @Test
    void deveRetornarTempoMaximoDuracaoCorretamente() {
        // Arrange
        Usuario usuario = Usuario.builder()
                .id(1L)
                .nome("João Silva")
                .build();
        
        RefreshToken refreshToken = RefreshToken.create(usuario);

        // Act
        long tempoMaximo = refreshToken.getTempoMaximoDuracaoSegundos();

        // Assert
        assertEquals(1800, tempoMaximo); // 30 minutos * 60 segundos
    }

    @Test
    void deveCriarRefreshTokenComDataHoraExpiracaoCorreta() {
        // Arrange
        Usuario usuario = Usuario.builder()
                .id(1L)
                .nome("João Silva")
                .build();

        // Act
        RefreshToken refreshToken = RefreshToken.create(usuario);
        LocalDateTime dataHoraExpiracao = refreshToken.getDataHoraExpiracao();
        LocalDateTime dataHoraCriacao = refreshToken.getDataHoraCriacao();

        // Assert
        assertEquals(30, dataHoraCriacao.getMinute() - dataHoraExpiracao.getMinute());
    }
}
