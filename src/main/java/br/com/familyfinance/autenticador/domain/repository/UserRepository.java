package br.com.familyfinance.autenticador.domain.repository;

import br.dev.paulocarvalho.arquitetura.domain.repository.BaseRepository;
import br.dev.paulocarvalho.autenticador.domain.model.User;
import io.smallrye.mutiny.Uni;

public interface UserRepository extends BaseRepository<User, Long> {
    Uni<User> findByUsername(String email);
}
