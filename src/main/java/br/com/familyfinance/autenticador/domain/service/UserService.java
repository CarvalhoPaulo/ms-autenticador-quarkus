package br.com.familyfinance.autenticador.domain.service;

import br.dev.paulocarvalho.autenticador.domain.model.User;
import io.smallrye.mutiny.Uni;

public interface UserService {

    Uni<User> findByUsername(String username);

}
