package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import edu.school21.models.User;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;

public class UsersServiceImplTest {
    private UsersRepository usersRepository;
    private UsersServiceImpl usersService;
    private User user1;
    private User user2;

    @BeforeEach
    public void init() {
        usersRepository = Mockito.mock(UsersRepository.class);
        usersService = new UsersServiceImpl(usersRepository);
        user1 = new User(1L, "user1", "qwerty", true);
        user2 = new User(1L, "user2", "123qwe", false);

        Mockito.when(usersRepository.findByLogin("user1")).thenReturn(user1);
        Mockito.when(usersRepository.findByLogin("user2")).thenReturn(user2);
        Mockito.when(usersRepository.findByLogin("user3"))
                .thenThrow(new EntityNotFoundException());
    }

    @Test
    public void testExpectedAlreadyAuthenticatedException() {
        Assertions.assertThrows(
                AlreadyAuthenticatedException.class,
                () -> usersService.authenticate(
                        user1.getLogin(), user1.getPassword())
        );
        Mockito.verify(usersRepository, Mockito.never()).update(any(User.class));
    }

    @Test
    public void testCorrectLoginPassword() {
        Assertions.assertTrue(usersService
                .authenticate(user2.getLogin(), user2.getPassword()));
    }

    @Test
    public void testExpectedEntityNotFoundException() {
        Assertions.assertThrows(
                RuntimeException.class,
                () -> new UsersServiceImpl(usersRepository)
                        .authenticate("user3", "000")
        );
    }

    @Test
    public void testWrongPassword() {
        Assertions.assertFalse(usersService
                .authenticate(user2.getLogin(), "wrong_password"));
    }

    @Test
    public void testIncorrectPasswd_true(){
        Assertions.assertThrows(
                AlreadyAuthenticatedException.class,
                () -> new UsersServiceImpl(usersRepository)
                        .authenticate(user1.getLogin(), "qwe123")
        );
    }
}
