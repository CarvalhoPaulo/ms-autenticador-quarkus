package br.com.familyfinance.autenticador.domain.service;

import br.com.familyfinance.autenticador.application.dto.LoginDTO;
import br.com.familyfinance.autenticador.application.dto.TokenDTO;
import io.smallrye.mutiny.Uni;

public interface AuthService {
    Uni<TokenDTO> login(LoginDTO loginDTO);

    Uni<TokenDTO> refreshToken(String refreshToken);
}
