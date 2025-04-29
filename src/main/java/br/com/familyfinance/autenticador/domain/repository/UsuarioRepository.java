package br.com.familyfinance.autenticador.domain.repository;

import br.com.familyfinance.arquitetura.domain.repository.BaseRepository;
import br.com.familyfinance.autenticador.domain.model.Usuario;
import io.smallrye.mutiny.Uni;

public interface UsuarioRepository extends BaseRepository<Usuario, Long> {
    Uni<Usuario> buscarPorEmail(String email);
}
