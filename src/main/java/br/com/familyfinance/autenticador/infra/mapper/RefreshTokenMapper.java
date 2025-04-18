package br.com.familyfinance.autenticador.infra.mapper;

import br.com.familyfinance.arquitetura.core.mapper.AbstractModelMapper;
import br.com.familyfinance.autenticador.core.model.RefreshToken;
import br.com.familyfinance.autenticador.infra.data.RefreshTokenData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UsuarioMapper.class})
public interface RefreshTokenMapper extends AbstractModelMapper<RefreshToken, RefreshTokenData> {
    RefreshTokenMapper INSTANCE = Mappers.getMapper(RefreshTokenMapper.class);
}
