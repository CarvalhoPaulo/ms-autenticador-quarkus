package br.com.familyfinance.autenticador.core.model;

import br.com.familyfinance.arquitetura.core.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class RefreshToken implements Model<Long> {
    private static final int MAX_AGE_MINUTES = 30;

    private Long id;
    private String token;
    private Usuario usuario;
    private LocalDateTime dataHoraCriacao;
    private LocalDateTime dataHoraExpiracao;

    public static RefreshToken create(Usuario usuario) {
        return RefreshToken.builder()
                .usuario(usuario)
                .token(UUID.randomUUID().toString())
                .dataHoraExpiracao(LocalDateTime.now().plusMinutes(MAX_AGE_MINUTES))
                .dataHoraCriacao(LocalDateTime.now())
                .build();
    }

    public long getTempoMaximoDuracaoSegundos() {
        return (MAX_AGE_MINUTES * 60);
    }

    public void renovarToken() {
        this.token = UUID.randomUUID().toString();
        this.dataHoraExpiracao = LocalDateTime.now().plusMinutes(RefreshToken.MAX_AGE_MINUTES);
    }
}
