package br.com.familyfinance.autenticador.domain.repository;

import br.dev.paulocarvalho.arquitetura.domain.exception.BusinessException;
import br.dev.paulocarvalho.autenticador.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username) throws BusinessException;
}
