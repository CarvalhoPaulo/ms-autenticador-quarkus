package br.com.familyfinance.autenticador.domain.model;

import br.com.familyfinance.autenticador.domain.exception.InvalidPasswordlException;
import br.com.familyfinance.autenticador.domain.exception.SenhaInvalidaException;
import io.quarkus.elytron.security.common.BcryptUtil;

public class BcryptPassword {

    private static final String PASSWORD_REGEX =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$";
    private static final String BCRYPT_REGEX = "^\\$2[aby]?\\$\\d{2}\\$[./A-Za-z0-9]{53}$\n";

    public static boolean isValid(String password) {
        if (password == null) {
            return false;
        }
        return password.matches(PASSWORD_REGEX);
    }

    private final String hashPassword;

    BcryptPassword(String password) throws InvalidPasswordlException {
        if (password.matches(BCRYPT_REGEX)) {
            this.hashPassword = password;
        } else {
            if (!isValid(password)) {
                throw new InvalidPasswordlException();
            }
            this.hashPassword = BcryptUtil.bcryptHash(password);
        }
    }

    public void checkPassword(String plainPassword) throws SenhaInvalidaException {
        if (!BcryptUtil.matches(plainPassword, hashPassword)) {
            throw new SenhaInvalidaException();
        }
    }

    @Override
    public String toString() {
        return hashPassword;
    }
}
