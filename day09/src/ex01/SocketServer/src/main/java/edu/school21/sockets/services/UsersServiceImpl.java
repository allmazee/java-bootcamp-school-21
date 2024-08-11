package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsersServiceImpl implements UsersService{
    private final UsersRepository usersRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder encoder) {
        this.usersRepository = usersRepository;
        this.encoder = encoder;
    }

    @Override
    public boolean signUp(String name, String password) {
        if (usersRepository.findByName(name).isPresent()) {
            return false;
        }
        usersRepository.save(new User(name, encoder.encode(password)));
        return true;
    }

    @Override
    public User signIn(String name, String password) {
        Optional<User> userOptional = usersRepository.findByName(name);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (encoder.matches(password, user.getPassword())) {
                if (!user.isLogged()) {
                    user.setLogged(true);
                    usersRepository.update(user);
                    return user;
                }
            }
        }
        return null;
    }

    @Override
    public void logOff(User user) {
        user.setLogged(false);
        usersRepository.update(user);
    }

    @Override
    public Optional<User> findByName(String name) {
        return usersRepository.findByName(name);
    }
}
