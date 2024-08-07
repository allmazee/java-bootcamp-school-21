package edu.school21.spring.service.repositories;

import edu.school21.spring.service.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UsersRepositoryJdbcImpl implements UsersRepository {
    private DataSource dataSource;

    @Autowired
    public UsersRepositoryJdbcImpl(@Qualifier("hikariDataSource")
                                       DataSource dataSource) {
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
                long userId = rs.getLong("id");
                String userEmail = rs.getString("email");
                String userPassword = rs.getString("password");
                user = new User(userId, userEmail, userPassword);
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
                long userId = rs.getLong("id");
                String userEmail = rs.getString("email");
                String userPassword = rs.getString("password");
                user = new User(userId, userEmail, userPassword);
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
                long userId = rs.getLong("id");
                String userEmail = rs.getString("email");
                String userPassword = rs.getString("password");
                User user = new User(userId, userEmail, userPassword);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void save(User entity) {
        String insertQuery = "INSERT INTO simple_user " +
                "(email, password) VALUES (?, ?);";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection
                    .prepareStatement(insertQuery);
            statement.setString(1, entity.getEmail());
            statement.setString(2, entity.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User entity) {
        String updateQuery = "UPDATE simple_user SET email = ? WHERE id = ?;";
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection
                    .prepareStatement(updateQuery);
            statement.setString(1, entity.getEmail());
            statement.setLong(2, entity.getId());
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
