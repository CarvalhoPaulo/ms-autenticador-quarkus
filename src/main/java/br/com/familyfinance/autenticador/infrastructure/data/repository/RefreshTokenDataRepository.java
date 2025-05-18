package br.com.familyfinance.autenticador.infrastructure.data.repository;


import br.dev.paulocarvalho.arquitetura.infrastructure.data.repository.AbstractDataRepository;
import br.dev.paulocarvalho.autenticador.domain.model.RefreshToken;
import br.com.familyfinance.autenticador.domain.repository.RefreshTokenRepository;
import br.com.familyfinance.autenticador.infrastructure.data.RefreshTokenData;
import br.com.familyfinance.autenticador.infrastructure.mapper.RefreshTokenMapper;
import br.dev.paulocarvalho.arquitetura.domain.mapper.AbstractModelMapper;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RefreshTokenDataRepository extends AbstractDataRepository<RefreshToken, RefreshTokenData, Long>
        implements RefreshTokenRepository {
    public Uni<RefreshToken> findByToken(String token) {
        return find("token", token)
                .firstResult()
                .onItem()
                .transform(Unchecked.function(RefreshTokenMapper.INSTANCE::toModel));
    }

    @Override
    protected AbstractModelMapper<RefreshToken, RefreshTokenData> getMapper() {
        return RefreshTokenMapper.INSTANCE;
    }
}
