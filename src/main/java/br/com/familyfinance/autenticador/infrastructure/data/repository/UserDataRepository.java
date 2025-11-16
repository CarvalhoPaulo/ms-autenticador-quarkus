package br.com.familyfinance.autenticador.infrastructure.data.repository;

import br.com.familyfinance.autenticador.domain.repository.UserRepository;
import br.com.familyfinance.autenticador.infrastructure.data.UserData;
import br.com.familyfinance.autenticador.infrastructure.mapper.UserMapper;
import br.dev.paulocarvalho.arquitetura.domain.exception.BusinessException;
import br.dev.paulocarvalho.arquitetura.domain.mapper.AbstractModelMapper;
import br.dev.paulocarvalho.arquitetura.infrastructure.data.repository.AbstractDataRepository;
import br.dev.paulocarvalho.autenticador.domain.model.User;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Optional;

@ApplicationScoped
public class UserDataRepository extends AbstractDataRepository<User, UserData, Long>
        implements UserRepository {

    public Optional<User> findByUsername(String username) throws BusinessException {
        var params = new HashMap<String, Object>();
        params.put("username", username);
        Optional<UserData> data = find("username = :username and active = true", params)
                .firstResultOptional();
        if (data.isPresent()) {
            return Optional.of(UserMapper.INSTANCE.toModel(data.get()));
        } else {
            return Optional.empty();
        }
    }

    @Override
    protected AbstractModelMapper<User, UserData> getMapper() {
        return UserMapper.INSTANCE;
    }
}
