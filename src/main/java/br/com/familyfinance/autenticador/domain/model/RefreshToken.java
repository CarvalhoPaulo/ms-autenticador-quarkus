package br.com.familyfinance.autenticador.domain.model;

import br.dev.paulocarvalho.arquitetura.domain.model.Model;
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
    private User user;
    private LocalDateTime creationDateTime;
    private LocalDateTime expirationDateTime;

    public static RefreshToken create(User user) {
        return RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expirationDateTime(LocalDateTime.now().plusMinutes(MAX_AGE_MINUTES))
                .creationDateTime(LocalDateTime.now())
                .build();
    }

    public long getTempoMaximoDuracaoSegundos() {
        return (MAX_AGE_MINUTES * 60);
    }

    public void renovarToken() {
        this.token = UUID.randomUUID().toString();
        this.expirationDateTime = LocalDateTime.now().plusMinutes(RefreshToken.MAX_AGE_MINUTES);
    }
}
