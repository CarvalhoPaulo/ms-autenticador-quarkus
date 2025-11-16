package br.com.familyfinance.autenticador.domain.service;

import br.dev.paulocarvalho.arquitetura.domain.exception.BusinessException;
import br.dev.paulocarvalho.autenticador.domain.exception.UserNotRegistredException;
import br.dev.paulocarvalho.autenticador.domain.model.User;

public interface UserService {

    User findByUsername(String username) throws BusinessException;

}
