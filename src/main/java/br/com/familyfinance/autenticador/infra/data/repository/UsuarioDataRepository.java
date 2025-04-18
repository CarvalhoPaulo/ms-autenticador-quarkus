package br.com.familyfinance.autenticador.infra.data.repository;

import br.com.familyfinance.arquitetura.core.mapper.AbstractModelMapper;
import br.com.familyfinance.arquitetura.infra.data.repository.AbstractDataRepository;
import br.com.familyfinance.autenticador.core.model.Usuario;
import br.com.familyfinance.autenticador.core.repository.UsuarioRepository;
import br.com.familyfinance.autenticador.infra.data.UsuarioData;
import br.com.familyfinance.autenticador.infra.mapper.UsuarioMapper;
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
