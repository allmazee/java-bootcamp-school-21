package edu.school21.models;

import java.util.Objects;

public class User {
    private long id;
    private String login;
    private String password;
    private boolean authStatus;

    public User(long id, String login, String password, boolean authStatus) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.authStatus = authStatus;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setAuthStatus(boolean auth_status) {
        this.authStatus = auth_status;
    }

    public boolean getAuthStatus() {
        return authStatus;
    }

    @Override
    public int hashCode() {
        return (int) id +
                login.hashCode() +
                password.hashCode() +
                ((authStatus) ? 1 : 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.getId()) &&
                Objects.equals(login, user.getLogin()) &&
                Objects.equals(password, user.getPassword()) &&
                Objects.equals(authStatus, user.getAuthStatus());
    }

    @Override
    public String toString() {
        return "{id=" + id +
                ", login='" + login + '\'' +
                ", password=" + password +
                ", authentication status=" + authStatus +
                '}';
    }
}