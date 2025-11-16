package br.com.familyfinance.autenticador.infrastructure.data.repository;


import br.com.familyfinance.autenticador.domain.repository.RefreshTokenRepository;
import br.com.familyfinance.autenticador.infrastructure.data.RefreshTokenData;
import br.com.familyfinance.autenticador.infrastructure.mapper.RefreshTokenMapper;
import br.dev.paulocarvalho.arquitetura.domain.exception.BusinessException;
import br.dev.paulocarvalho.arquitetura.domain.mapper.AbstractModelMapper;
import br.dev.paulocarvalho.arquitetura.infrastructure.data.repository.AbstractDataRepository;
import br.dev.paulocarvalho.autenticador.domain.model.RefreshToken;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class RefreshTokenDataRepository extends AbstractDataRepository<RefreshToken, RefreshTokenData, Long>
        implements RefreshTokenRepository {

    public Optional<RefreshToken> findByToken(String token) throws BusinessException {
        Optional<RefreshTokenData> data = find("token", token)
                .firstResultOptional();
        if (data.isPresent()) {
            return Optional.of(RefreshTokenMapper.INSTANCE.toModel(data.get()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    protected AbstractModelMapper<RefreshToken, RefreshTokenData> getMapper() {
        return RefreshTokenMapper.INSTANCE;
    }
}
