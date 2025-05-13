package br.com.familyfinance.autenticador.domain.model;

import br.com.familyfinance.autenticador.domain.exception.InvalidProfileFeaturesException;
import br.com.familyfinance.autenticador.domain.exception.InvalidProfileNameException;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Getter
public class Profile {
    private final Long id;
    private final String name;
    private final List<Feature> features;

    public Profile(Long id, String name, List<Feature> features) throws InvalidProfileNameException,
            InvalidProfileFeaturesException {
        if (StringUtils.isEmpty(name)) {
            throw new InvalidProfileNameException();
        }
        if (features == null || features.isEmpty()) {
            throw new InvalidProfileFeaturesException();
        }
        this.id = id;
        this.name = name;
        this.features = features;
    }

    public boolean hasAccess(Feature funcionalidade) {
        return features.contains(funcionalidade);
    }
}
