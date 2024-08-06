package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private DataSource dataSource;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> findAll(int page, int size) {
        String selectQuery = "WITH user_pagination AS ( " +
                "SELECT id AS user_id, login, password " +
                "FROM users ORDER BY id LIMIT ? OFFSET ? ), " +
                "created_rooms AS ( SELECT owner AS user_id, " +
                "id AS room_id, name FROM chatrooms WHERE owner IN ( " +
                "SELECT user_id FROM user_pagination ) ), " +
                "participated_rooms AS ( SELECT DISTINCT m.author AS user_id," +
                "u.login AS user_name, u.password, cr.id AS room_id, cr.name " +
                "FROM chatrooms cr " +
                "JOIN messages m ON m.room = cr.id " +
                "JOIN users u ON u.id = cr.owner " +
                "WHERE m.author IN ( " +
                "SELECT user_id FROM user_pagination ) ) " +
                "SELECT up.user_id, up.login, up.password, " +
                "cr.room_id AS created_room_id,cr.name AS created_room_name, " +
                "pr.room_id AS participated_room_id, " +
                "pr.name AS participated_room_name, " +
                "pr.user_id AS participated_room_owner_id, " +
                "pr.user_name AS participated_room_owner_login, " +
                "pr.password AS participated_room_owner_password " +
                "FROM user_pagination up " +
                "LEFT JOIN created_rooms cr ON cr.user_id = up.user_id " +
                "LEFT JOIN participated_rooms pr ON pr.user_id = up.user_id " +
                "ORDER BY user_id, participated_room_id;";

        Map<Long, User> userMap = new HashMap<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(selectQuery);
            int offset = page * size;
            pstmt.setInt(1, size);
            pstmt.setInt(2, offset);
            ResultSet rs = pstmt.executeQuery();

            User user = null;
            while (rs.next()) {
                long userId = rs.getLong("user_id");
                if (user == null || user.getId() != userId) {
                    user = new User(rs.getLong("user_id"),
                            rs.getString("login"),
                            rs.getString("password"),
                            new ArrayList<>(), new ArrayList<>());
                    userMap.put(userId, user);
                }

                long createdRoomId = rs.getLong("created_room_id");
                String createdRoomName = rs.getString("created_room_name");
                Chatroom createdRoom = new Chatroom(createdRoomId,
                        createdRoomName, user, null);
                if (!user.getCreatedRooms().contains(createdRoom)) {
                    user.getCreatedRooms().add(createdRoom);
                }

                long ownerId = rs.getLong("participated_room_owner_id");
                String ownerLogin = rs.getString(
                        "participated_room_owner_login");
                String ownerPassword = rs.getString(
                        "participated_room_owner_password");
                User owner = new User(ownerId, ownerLogin, ownerPassword,
                        null, null);

                long participatedRoomId = rs.getLong("participated_room_id");
                String participatedRoomName = rs.getString(
                        "participated_room_name");
                Chatroom participatedRoom = new Chatroom(participatedRoomId,
                        participatedRoomName, owner, null);
                if (!user.getSocializedRooms().contains(participatedRoom)) {
                    user.getSocializedRooms().add(participatedRoom);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>(userMap.values());
    }
}
