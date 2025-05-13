package br.com.familyfinance.autenticador.infrastructure.mapper;

import br.com.familyfinance.autenticador.domain.model.RefreshToken;
import br.com.familyfinance.autenticador.infrastructure.data.RefreshTokenData;
import br.dev.paulocarvalho.arquitetura.domain.mapper.AbstractModelMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UserMapper.class})
public interface RefreshTokenMapper extends AbstractModelMapper<RefreshToken, RefreshTokenData> {
    RefreshTokenMapper INSTANCE = Mappers.getMapper(RefreshTokenMapper.class);
}
