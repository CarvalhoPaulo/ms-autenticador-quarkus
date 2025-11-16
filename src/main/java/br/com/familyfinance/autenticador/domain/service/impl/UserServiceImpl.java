package br.com.familyfinance.autenticador.domain.service.impl;

import br.com.familyfinance.autenticador.domain.repository.UserRepository;
import br.com.familyfinance.autenticador.domain.service.UserService;
import br.dev.paulocarvalho.arquitetura.domain.exception.BusinessException;
import br.dev.paulocarvalho.autenticador.domain.exception.UserNotRegistredException;
import br.dev.paulocarvalho.autenticador.domain.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    @Inject
    UserRepository userRepository;

    @Override
    public User findByUsername(String username) throws BusinessException {
        return userRepository.findByUsername(username)
                .orElseThrow(UserNotRegistredException::new);
    }
}
