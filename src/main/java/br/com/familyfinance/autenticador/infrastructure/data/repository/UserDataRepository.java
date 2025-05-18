package br.com.familyfinance.autenticador.infrastructure.data.repository;

import br.dev.paulocarvalho.arquitetura.infrastructure.data.repository.AbstractDataRepository;
import br.dev.paulocarvalho.autenticador.domain.model.User;
import br.com.familyfinance.autenticador.domain.repository.UserRepository;
import br.com.familyfinance.autenticador.infrastructure.data.UserData;
import br.com.familyfinance.autenticador.infrastructure.mapper.UserMapper;
import br.dev.paulocarvalho.arquitetura.domain.mapper.AbstractModelMapper;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;

@ApplicationScoped
public class UserDataRepository extends AbstractDataRepository<User, UserData, Long>
        implements UserRepository {
    public Uni<User> findByUsername(String username) {
        var params = new HashMap<String, Object>();
        params.put("username", username);
        return find("username = :username and active = true", params)
                .firstResult()
                .onItem()
                .transform(Unchecked.function(UserMapper.INSTANCE::toModel));
    }

    @Override
    protected AbstractModelMapper<User, UserData> getMapper() {
        return UserMapper.INSTANCE;
    }
}
