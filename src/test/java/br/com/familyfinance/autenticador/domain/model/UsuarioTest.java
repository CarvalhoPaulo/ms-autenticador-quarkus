package br.com.familyfinance.autenticador.domain.model;

import br.com.familyfinance.autenticador.domain.exception.InvalidEmailException;
import br.com.familyfinance.autenticador.domain.exception.InvalidFullNameException;
import br.com.familyfinance.autenticador.domain.exception.InvalidPasswordlException;
import br.com.familyfinance.autenticador.domain.exception.InvalidUsernameException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void deveCriarUsuarioComSucesso() throws InvalidPasswordlException, InvalidUsernameException, InvalidFullNameException, InvalidEmailException {
        // Arrange
        Long id = 1L;
        String name = "João Silva";
        String username = "joao.silva";
        String email = "joao@email.com";
        String password = "Senha@123";

        // Act
        User usuario = User.builder()
                .id(id)
                .username(username)
                .name(name)
                .email(email)
                .plainPassword(password)
                .build();

        // Assert
        assertNotNull(usuario);
        assertEquals(id, usuario.getId());
        assertEquals(name, usuario.getName());
        assertEquals(email, usuario.getEmail());
        assertDoesNotThrow(() -> usuario.checkPassword(password));
    }

}
