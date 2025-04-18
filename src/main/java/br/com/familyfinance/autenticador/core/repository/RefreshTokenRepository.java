package br.com.familyfinance.autenticador.core.repository;

import br.com.familyfinance.arquitetura.core.repository.BaseRepository;
import br.com.familyfinance.autenticador.core.model.RefreshToken;
import io.smallrye.mutiny.Uni;

public interface RefreshTokenRepository extends BaseRepository<RefreshToken, Long> {
    Uni<RefreshToken> buscarPorToken(String token);
}
