package edu.school21.chat.app;

import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        DataSource dataSource = DatabaseConnector.getDataSource();
        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource);
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("-> ");
            Long id = scanner.nextLong();
            Optional<Message> message = messagesRepository.findById(id);
            message.ifPresent(System.out::println);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
