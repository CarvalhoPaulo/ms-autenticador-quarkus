package br.com.familyfinance.autenticador.domain.service.impl;

import br.dev.paulocarvalho.autenticador.domain.exception.UserNotRegistredException;
import br.dev.paulocarvalho.autenticador.domain.model.User;
import br.com.familyfinance.autenticador.domain.repository.UserRepository;
import br.com.familyfinance.autenticador.domain.service.UserService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    UserRepository userRepository;

    @Override
    public Uni<User> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .onItem().ifNull().failWith(new UserNotRegistredException());
    }
}
