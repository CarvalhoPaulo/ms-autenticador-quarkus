package br.com.familyfinance.autenticador.infra.mapper;

import br.com.familyfinance.arquitetura.core.mapper.AbstractModelMapper;
import br.com.familyfinance.autenticador.core.model.Usuario;
import br.com.familyfinance.autenticador.infra.data.UsuarioData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UsuarioMapper extends AbstractModelMapper<Usuario, UsuarioData> {
    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);
}
