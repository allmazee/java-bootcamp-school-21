package edu.school21.chat.app;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;
import edu.school21.chat.repositories.NotSavedSubEntityException;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

public class Program {
    public static void main(String[] args) {
        User creator = new User(3L, "maplesar", "bavlycity123",
                new ArrayList<>(), new ArrayList<>());
        User author = creator;
        Chatroom room = new Chatroom(3L, "rules", creator, new ArrayList<>());
        Message message = new Message(-1, author, room,
                "Hello!", Timestamp.from(Instant.now()));
        DataSource dataSource = DatabaseConnector.getDataSource();
        MessagesRepository messagesRepository =
                new MessagesRepositoryJdbcImpl(dataSource);
        try {
            messagesRepository.save(message);
            System.out.println(message.getId());
        } catch (NotSavedSubEntityException e) {
            System.out.println(e.getMessage());
        }
    }
}
