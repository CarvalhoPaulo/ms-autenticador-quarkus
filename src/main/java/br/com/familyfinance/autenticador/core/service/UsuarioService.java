package br.com.familyfinance.autenticador.core.service;

import br.com.familyfinance.autenticador.core.model.Usuario;
import io.smallrye.mutiny.Uni;

public interface UsuarioService {

    Uni<Usuario> buscarPorEmail(String email);
}
