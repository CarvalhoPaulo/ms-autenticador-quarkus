package br.com.familyfinance.autenticador.infrastructure.data.repository;

import br.com.familyfinance.arquitetura.infrastructure.mapper.AbstractModelMapper;
import br.com.familyfinance.arquitetura.infrastructure.data.repository.AbstractDataRepository;
import br.com.familyfinance.autenticador.domain.model.RefreshToken;
import br.com.familyfinance.autenticador.domain.repository.RefreshTokenRepository;
import br.com.familyfinance.autenticador.infrastructure.data.RefreshTokenData;
import br.com.familyfinance.autenticador.infrastructure.mapper.RefreshTokenMapper;
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
