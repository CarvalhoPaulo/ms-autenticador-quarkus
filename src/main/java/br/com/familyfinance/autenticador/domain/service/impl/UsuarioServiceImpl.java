package br.com.familyfinance.autenticador.domain.service.impl;

import br.com.familyfinance.autenticador.domain.exception.UsuarioNaoCadastradoException;
import br.com.familyfinance.autenticador.domain.model.Usuario;
import br.com.familyfinance.autenticador.domain.repository.UsuarioRepository;
import br.com.familyfinance.autenticador.domain.service.UsuarioService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;

    @Override
    public Uni<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.buscarPorEmail(email)
                .onItem().ifNull().failWith(new UsuarioNaoCadastradoException());
    }
}
