package br.com.familyfinance.autenticador.domain.model;

import br.com.familyfinance.autenticador.domain.exception.*;
import br.dev.paulocarvalho.arquitetura.domain.model.Model;
import lombok.Builder;
import lombok.Getter;


public class User implements Model<Long> {
    @Getter
    private final Long id;
    private final FullName name;
    private final Username username;
    private final Email email;
    private final BcryptPassword password;
    @Getter
    private final Profile profile;
    @Getter
    private boolean active = true;

    @Builder
    public User(Long id,
                String name,
                String username,
                String email,
                String plainPassword,
                Profile profile) throws InvalidEmailException,
            InvalidPasswordlException,
            InvalidUsernameException,
            InvalidFullNameException {
        this.id = id;
        this.name = new FullName(name);
        this.username = new Username(username);
        this.email = new Email(email);
        this.password = new BcryptPassword(plainPassword);
        this.profile = profile;
    }

    public User checkPassword(String plainPassword) throws SenhaInvalidaException {
        this.password.checkPassword(plainPassword);
        return this;
    }

    public User delete() {
        this.active = false;
        return this;
    }

    public String getName() {
        return name.toString();
    }

    public String getUsername() {
        return username.toString();
    }

    public String getEmail() {
        return email.toString();
    }

}
