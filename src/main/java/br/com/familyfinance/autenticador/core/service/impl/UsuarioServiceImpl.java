package br.com.familyfinance.autenticador.core.service.impl;

import br.com.familyfinance.autenticador.core.model.Usuario;
import br.com.familyfinance.autenticador.core.repository.UsuarioRepository;
import br.com.familyfinance.autenticador.core.service.UsuarioService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;

    @Override
    public Uni<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.buscarPorEmail(email);
    }
}
