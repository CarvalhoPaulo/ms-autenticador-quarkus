package br.com.familyfinance.autenticador.domain.model;

import br.com.familyfinance.arquitetura.domain.model.Model;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Usuario implements Model<Long> {
    private Long id;
    private String nome;
    private String email;
    private String senha;
}
