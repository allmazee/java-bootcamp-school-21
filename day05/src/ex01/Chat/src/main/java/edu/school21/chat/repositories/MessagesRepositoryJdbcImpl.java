package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private DataSource dataSource;
    private String selectQuery;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.selectQuery = "SELECT m.id AS message_id, m.text, m.datetime, " +
                "u.id AS user_id, u.login, u.password, " +
                "c.id AS room_id, c.name\n" +
                "FROM messages m\n" +
                "JOIN users u ON u.id = m.author\n" +
                "JOIN chatrooms c ON c.id = m.room\n" +
                "WHERE m.id = ?";
    }

    @Override
    public Optional<Message> findById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(selectQuery);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User author = new User(rs.getLong("user_id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        null, null);
                Chatroom room = new Chatroom(rs.getLong("room_id"),
                        rs.getString("name"),
                        null, null);
                Message message = new Message(id, author, room,
                        rs.getString("text"),
                        rs.getTimestamp("datetime"));
                return Optional.of(message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }
}
