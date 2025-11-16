package br.com.familyfinance.autenticador.infrastructure.data;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
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

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserData user;

    @Column(name = "creationDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationDateTime;

    @Column(name = "expirationDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime expirationDateTime;

}
