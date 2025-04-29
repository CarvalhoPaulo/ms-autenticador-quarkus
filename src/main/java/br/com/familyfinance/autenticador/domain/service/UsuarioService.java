package br.com.familyfinance.autenticador.domain.service;

import br.com.familyfinance.autenticador.domain.model.Usuario;
import io.smallrye.mutiny.Uni;

public interface UsuarioService {

    Uni<Usuario> buscarPorEmail(String email);
}
