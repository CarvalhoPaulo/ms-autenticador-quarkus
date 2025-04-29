package br.com.familyfinance.autenticador.infrastructure.mapper;

import br.com.familyfinance.arquitetura.infrastructure.mapper.AbstractModelMapper;
import br.com.familyfinance.autenticador.domain.model.Usuario;
import br.com.familyfinance.autenticador.infrastructure.data.UsuarioData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UsuarioMapper extends AbstractModelMapper<Usuario, UsuarioData> {
    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);
}
