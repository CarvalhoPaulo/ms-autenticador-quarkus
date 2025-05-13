package br.com.familyfinance.autenticador.domain.service;

import br.com.familyfinance.autenticador.domain.model.User;
import io.smallrye.mutiny.Uni;

public interface UsuarioService {

    Uni<User> findByUsername(String username);
}
