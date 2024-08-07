package edu.school21.spring.service.services;

import edu.school21.spring.service.config.TestApplicationConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UsersServiceImplTest {
    private UsersService usersService;

    @BeforeEach
    public void init() {
        ApplicationContext context = new AnnotationConfigApplicationContext(
                TestApplicationConfig.class);
        usersService = context.getBean("usersService", UsersServiceImpl.class);
    }

    @Test
    public void testSignUpThatReturnsPassword() {
        Assertions.assertNotNull(usersService.signUp("new-mail@mail.ru"));
    }

    @Test
    public void testSignUpThatReturnsNull() {
        Assertions.assertNull(usersService.signUp("alimovar@stud.kai.ru"));
    }
}
