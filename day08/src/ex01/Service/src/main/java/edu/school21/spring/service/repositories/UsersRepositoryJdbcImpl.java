package edu.school21.spring.service.repositories;

import edu.school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private DataSource dataSource;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User user = null;
        String selectQuery = "SELECT * FROM simple_user WHERE email = ?";
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection
                    .prepareStatement(selectQuery);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Long userId = rs.getLong("id");
                String userEmail = rs.getString("email");
                user = new User(userId, userEmail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = null;
        String selectQuery = "SELECT * FROM simple_user WHERE id = ?;";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement(selectQuery);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Long userId = rs.getLong("id");
                String userEmail = rs.getString("email");
                user = new User(userId, userEmail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List findAll() {
        List<User> users = new ArrayList<>();
        String selectQuery = "SELECT * FROM simple_user;";
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectQuery);
            while (rs.next()) {
                Long userId = rs.getLong("id");
                String userEmail = rs.getString("email");
                User user = new User(userId, userEmail);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void save(Object entity) {
        String insertQuery = "INSERT INTO simple_user (email) VALUES (?);";
        try (Connection connection = dataSource.getConnection()) {
            User user = (User) entity;
            PreparedStatement statement = connection
                    .prepareStatement(insertQuery);
            statement.setString(1, user.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Object entity) {
        String updateQuery = "UPDATE simple_user SET email = ? WHERE id = ?;";
        try (Connection connection = dataSource.getConnection()){
            User user = (User) entity;
            PreparedStatement statement = connection
                    .prepareStatement(updateQuery);
            statement.setString(1, user.getEmail());
            statement.setLong(2, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String deleteQuery = "DELETE FROM simple_user WHERE id = ?";
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection
                    .prepareStatement(deleteQuery);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
