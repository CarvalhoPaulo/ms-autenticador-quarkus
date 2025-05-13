package br.com.familyfinance.autenticador.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
@Builder
@AllArgsConstructor
public class Feature {
    private Long id;
    private String nome;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Feature that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
