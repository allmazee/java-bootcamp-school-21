package edu.school21.spring.service.application;

import edu.school21.spring.service.config.ApplicationConfig;
import edu.school21.spring.service.repositories.UsersRepository;
import edu.school21.spring.service.services.UsersService;
import edu.school21.spring.service.services.UsersServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(
                ApplicationConfig.class);
        UsersRepository usersRepository = context.getBean("usersRepositoryJdbc",
                UsersRepository.class);
        System.out.println(usersRepository.findAll());
        usersRepository = context.getBean("usersRepositoryJdbcTemplate",
                UsersRepository.class);
        System.out.println(usersRepository.findAll());
        UsersService usersService = context.getBean(UsersServiceImpl.class);
        System.out.println(usersService.signUp("hiiii@gmail.com"));
    }
}
