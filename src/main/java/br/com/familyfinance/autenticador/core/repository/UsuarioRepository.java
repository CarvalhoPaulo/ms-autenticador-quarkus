package br.com.familyfinance.autenticador.core.repository;

import br.com.familyfinance.arquitetura.core.repository.BaseRepository;
import br.com.familyfinance.autenticador.core.model.Usuario;
import io.smallrye.mutiny.Uni;

public interface UsuarioRepository extends BaseRepository<Usuario, Long> {
    Uni<Usuario> buscarPorEmail(String email);
}
