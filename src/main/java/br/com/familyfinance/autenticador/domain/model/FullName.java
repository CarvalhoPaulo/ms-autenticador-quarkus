package br.com.familyfinance.autenticador.domain.model;

import br.com.familyfinance.autenticador.domain.exception.InvalidFullNameException;

import java.util.regex.Pattern;

public class FullName {
    private static final String REGEX = "^(\\p{Lu}\\p{Ll}+)([\\s-]\\p{Lu}\\p{Ll}+)+$";
    private static final Pattern PATTERN = Pattern.compile(REGEX, Pattern.UNICODE_CHARACTER_CLASS | Pattern.UNICODE_CASE);

    private final String name;

    public FullName(String name) throws InvalidFullNameException {
        if (!isValid(name)) {
            throw new InvalidFullNameException();
        }
        this.name = name;
    }

    public static boolean isValid(String name) {
        if (name == null) {
            return false;
        }
        return PATTERN.matcher(name).matches();
    }

    @Override
    public String toString() {
        return name;
    }
}
