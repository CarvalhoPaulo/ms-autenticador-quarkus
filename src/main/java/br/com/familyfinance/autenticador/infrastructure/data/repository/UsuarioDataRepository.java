package br.com.familyfinance.autenticador.infrastructure.data.repository;

import br.com.familyfinance.arquitetura.infrastructure.mapper.AbstractModelMapper;
import br.com.familyfinance.arquitetura.infrastructure.data.repository.AbstractDataRepository;
import br.com.familyfinance.autenticador.domain.model.Usuario;
import br.com.familyfinance.autenticador.domain.repository.UsuarioRepository;
import br.com.familyfinance.autenticador.infrastructure.data.UsuarioData;
import br.com.familyfinance.autenticador.infrastructure.mapper.UsuarioMapper;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioDataRepository extends AbstractDataRepository<Usuario, UsuarioData, Long>
        implements UsuarioRepository {
    public Uni<Usuario> buscarPorEmail(String email) {
        return find("email", email)
                .firstResult()
                .onItem()
                .transform(UsuarioMapper.INSTANCE::toModel);
    }

    @Override
    protected AbstractModelMapper<Usuario, UsuarioData> getMapper() {
        return UsuarioMapper.INSTANCE;
    }
}
