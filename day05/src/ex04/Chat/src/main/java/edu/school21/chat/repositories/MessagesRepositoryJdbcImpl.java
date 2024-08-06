package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) {
        String selectQuery;
        try (Connection connection = dataSource.getConnection()) {
            selectQuery = "SELECT m.id AS message_id, m.text, m.datetime, " +
                    "u.id AS user_id, u.login, u.password, " +
                    "c.id AS room_id, c.name\n" +
                    "FROM messages m\n" +
                    "JOIN users u ON u.id = m.author\n" +
                    "JOIN chatrooms c ON c.id = m.room\n" +
                    "WHERE m.id = ?;";
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

    @Override
    public void save(Message message) {
        String insertQuery = "INSERT INTO messages " +
                "(author, room, text, datetime) VALUES (?, ?, ?, ?);";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(insertQuery);
            pstmt.setLong(1, message.getAuthor().getId());
            pstmt.setLong(2, message.getChatroom().getId());
            pstmt.setString(3, message.getText());

            pstmt.setTimestamp(4, message.getDateTime());
            pstmt.executeUpdate();

            String selectQuery = "SELECT MAX(id) AS id FROM messages;";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(selectQuery);
            if (rs.next()) {
                message.setId(rs.getLong("id"));
            } else {
                throw new NotSavedSubEntityException(
                        "NotSavedSubEntityException: Sub entity was not saved");
            }
        } catch (SQLException | RuntimeException e) {
            throw new NotSavedSubEntityException(
                    "NotSavedSubEntityException: " + e.getMessage());
        }
    }

    @Override
    public void update(Message message) {
        String updateQuery = "UPDATE messages SET author = ?, " +
                "room = ?, text = ?, datetime = ? WHERE id = ?;";
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement pstmt = connection.prepareStatement(updateQuery);
            pstmt.setLong(1, message.getAuthor().getId());
            pstmt.setLong(2, message.getChatroom().getId());
            pstmt.setString(3, message.getText());
            pstmt.setTimestamp(4, message.getDateTime());
            pstmt.setLong(5, message.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
