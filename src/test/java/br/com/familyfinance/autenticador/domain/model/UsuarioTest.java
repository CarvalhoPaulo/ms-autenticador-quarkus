package br.com.familyfinance.autenticador.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void deveCriarUsuarioComSucesso() {
        // Arrange
        Long id = 1L;
        String nome = "João Silva";
        String email = "joao@email.com";
        String senha = "senha123";

        // Act
        Usuario usuario = Usuario.builder()
                .id(id)
                .nome(nome)
                .email(email)
                .senha(senha)
                .build();

        // Assert
        assertNotNull(usuario);
        assertEquals(id, usuario.getId());
        assertEquals(nome, usuario.getNome());
        assertEquals(email, usuario.getEmail());
        assertEquals(senha, usuario.getSenha());
    }

}
