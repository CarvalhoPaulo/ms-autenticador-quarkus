package br.com.familyfinance.autenticador.core.model;

import br.com.familyfinance.arquitetura.core.Model;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class RefreshToken implements Model<Long> {
    private static final int MAX_AGE_MINUTES = 30;

    private Long id;
    private String token;
    private Usuario usuario;
    private LocalDateTime expiration =  LocalDateTime.now().plusMinutes(RefreshToken.MAX_AGE_MINUTES);

    @Builder
    public RefreshToken(Usuario usuario, String token, Long id) {
        this.usuario = usuario;
        this.token = token;
        this.id = id;
    }

    public long getTempoMaximoDuracaoSegundos() {
        return (MAX_AGE_MINUTES * 60);
    }
}
