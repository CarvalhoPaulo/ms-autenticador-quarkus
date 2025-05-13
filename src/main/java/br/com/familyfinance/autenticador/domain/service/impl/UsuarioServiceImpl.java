package br.com.familyfinance.autenticador.domain.service.impl;

import br.com.familyfinance.autenticador.domain.exception.UserNotRegistredException;
import br.com.familyfinance.autenticador.domain.model.User;
import br.com.familyfinance.autenticador.domain.repository.UserRepository;
import br.com.familyfinance.autenticador.domain.service.UsuarioService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UserRepository userRepository;

    @Override
    public Uni<User> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .onItem().ifNull().failWith(new UserNotRegistredException());
    }
}
