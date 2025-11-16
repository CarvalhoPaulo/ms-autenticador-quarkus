package br.com.familyfinance.autenticador.domain.repository;

import br.dev.paulocarvalho.arquitetura.domain.exception.BusinessException;
import br.dev.paulocarvalho.arquitetura.domain.repository.BaseRepository;
import br.dev.paulocarvalho.autenticador.domain.model.RefreshToken;
import io.smallrye.mutiny.Uni;

import java.util.Optional;

public interface RefreshTokenRepository extends BaseRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token) throws BusinessException;
}
