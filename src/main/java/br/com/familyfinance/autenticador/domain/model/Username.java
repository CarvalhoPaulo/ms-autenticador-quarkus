package br.com.familyfinance.autenticador.domain.model;

import br.com.familyfinance.autenticador.domain.exception.InvalidUsernameException;

public class Username {
    private static final String USERNAME_REGEX = "^(?=.{3,50}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";

    private final String username;

    public Username(String username) throws InvalidUsernameException {
        if (!isValid(username)) {
            throw new InvalidUsernameException();
        }
        this.username = username;
    }

    public static boolean isValid(String email) {
        if (email == null) {
            return false;
        }
        return email.matches(USERNAME_REGEX);
    }

    @Override
    public String toString() {
        return username;
    }
}
