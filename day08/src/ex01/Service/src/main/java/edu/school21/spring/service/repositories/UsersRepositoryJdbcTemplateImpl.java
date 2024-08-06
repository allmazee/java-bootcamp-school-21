package edu.school21.spring.service.repositories;

import edu.school21.spring.service.models.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
    private JdbcTemplate jdbcTemplate;

    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jdbcTemplate.query("SELECT * FROM simple_user WHERE email = ?",
                new Object[]{email},
                new BeanPropertyRowMapper<>(User.class)).stream().findAny();
    }

    @Override
    public Optional<User> findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM simple_user WHERE id = ?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(User.class)).stream().findAny();
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM simple_user",
                new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public void save(Object entity) {
        User user = (User) entity;
        jdbcTemplate.update("INSERT INTO simple_user (email) VALUES (?)",
                user.getEmail());
    }

    @Override
    public void update(Object entity) {
       User user = (User) entity;
       jdbcTemplate.update("UPDATE simple_user SET email = ? WHERE id = ?",
               user.getEmail(), user.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM simple_user WHERE id = ?", id);
    }
}
