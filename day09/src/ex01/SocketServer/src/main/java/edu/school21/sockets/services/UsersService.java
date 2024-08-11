package edu.school21.sockets.services;

import edu.school21.sockets.models.User;

import java.util.Optional;

public interface UsersService {
    boolean signUp(String name, String password);
    User signIn(String name, String password);
    void logOff(User user);
    Optional<User> findByName(String name);
}
