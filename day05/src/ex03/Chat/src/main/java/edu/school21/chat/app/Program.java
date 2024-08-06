package edu.school21.chat.app;

import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

public class Program {
    public static void main(String[] args) {
        DataSource dataSource = DatabaseConnector.getDataSource();
        MessagesRepository messagesRepository =
                new MessagesRepositoryJdbcImpl(dataSource);
        Optional<Message> messageOptional = messagesRepository.findById(12L);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            System.out.println(message);
            message.setText("Bye...");
            message.setDateTime(Timestamp.from(Instant.now()));
            try {
                messagesRepository.update(message);
            } catch (RuntimeException e){
                System.out.println(e.getMessage());
            }
            System.out.println(message);
        }
    }
}
