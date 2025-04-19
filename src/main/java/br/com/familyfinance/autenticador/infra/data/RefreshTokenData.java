package br.com.familyfinance.autenticador.infra.data;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_token")
@Getter
@Setter
public class RefreshTokenData extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "token")
    private String token;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private UsuarioData usuario;

    @Column(name = "dataHoraCriacao")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataHoraCriacao;

    @Column(name = "dataHoraExpiracao")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dataHoraExpiracao;

}
