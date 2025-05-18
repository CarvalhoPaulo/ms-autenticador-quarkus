package br.com.familyfinance.autenticador.domain.repository;

import br.dev.paulocarvalho.arquitetura.domain.repository.BaseRepository;
import br.dev.paulocarvalho.autenticador.domain.model.RefreshToken;
import io.smallrye.mutiny.Uni;

public interface RefreshTokenRepository extends BaseRepository<RefreshToken, Long> {
    Uni<RefreshToken> findByToken(String token);
}
