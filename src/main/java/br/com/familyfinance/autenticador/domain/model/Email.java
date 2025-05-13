package br.com.familyfinance.autenticador.domain.model;

import br.com.familyfinance.autenticador.domain.exception.InvalidEmailException;

public class Email {
    private static final String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";

    private final String email;

    public Email(String email) throws InvalidEmailException {
        if (!isValid(email)) {
            throw new InvalidEmailException();
        }
        this.email = email;
    }

    public static boolean isValid(String email) {
        if (email == null) {
            return false;
        }
        return email.matches(EMAIL_REGEX);
    }

    @Override
    public String toString() {
        return email;
    }
}
