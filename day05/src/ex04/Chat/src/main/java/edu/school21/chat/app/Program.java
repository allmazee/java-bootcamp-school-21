package edu.school21.chat.app;

import edu.school21.chat.models.User;
import edu.school21.chat.repositories.UsersRepository;
import edu.school21.chat.repositories.UsersRepositoryJdbcImpl;

import javax.sql.DataSource;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        DataSource dataSource = DatabaseConnector.getDataSource();
        UsersRepository usersRepository = new UsersRepositoryJdbcImpl(dataSource);
        try {
            List<User> users = usersRepository.findAll(0, 5);
            for (User user : users) {
                System.out.println(user);
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }


    }
}
