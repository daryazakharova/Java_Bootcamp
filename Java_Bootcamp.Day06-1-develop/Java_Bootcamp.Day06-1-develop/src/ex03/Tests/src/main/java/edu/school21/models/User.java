package edu.school21.models;

import java.util.Objects;

public class User {
    private Long identifier;
    private String login;
    private String password;
    private boolean authenticated;

    public User(Long identifier, String login, String password) {
        this.identifier = identifier;
        this.login = login;
        this.password = password;
        this.authenticated = false;
    }

    public Long getIdentifier() {
        return identifier;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(identifier, user.identifier) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, login, password);
    }

    @Override
    public String toString() {
        return "User: " +
                "id= " + identifier +
                ", login= " + login +
                ", password= " + password +
                ", authenticated= " + authenticated;
    }
}
