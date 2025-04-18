package br.com.familyfinance.autenticador.infra.data.repository;

import br.com.familyfinance.arquitetura.core.mapper.AbstractModelMapper;
import br.com.familyfinance.arquitetura.infra.data.repository.AbstractDataRepository;
import br.com.familyfinance.autenticador.core.model.RefreshToken;
import br.com.familyfinance.autenticador.core.repository.RefreshTokenRepository;
import br.com.familyfinance.autenticador.infra.data.RefreshTokenData;
import br.com.familyfinance.autenticador.infra.mapper.RefreshTokenMapper;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RefreshTokenDataRepository extends AbstractDataRepository<RefreshToken, RefreshTokenData, Long>
        implements RefreshTokenRepository {
    public Uni<RefreshToken> buscarPorToken(String token) {
        return find("token", token)
                .firstResult()
                .onItem()
                .transform(RefreshTokenMapper.INSTANCE::toModel);
    }

    @Override
    protected AbstractModelMapper<RefreshToken, RefreshTokenData> getMapper() {
        return RefreshTokenMapper.INSTANCE;
    }
}
