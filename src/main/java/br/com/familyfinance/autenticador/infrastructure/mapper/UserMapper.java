package br.com.familyfinance.autenticador.infrastructure.mapper;

import br.com.familyfinance.autenticador.domain.model.*;
import br.com.familyfinance.autenticador.infrastructure.data.UserData;
import br.dev.paulocarvalho.arquitetura.domain.mapper.AbstractModelMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class UserMapper implements AbstractModelMapper<User, UserData> {
    public static UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
}
