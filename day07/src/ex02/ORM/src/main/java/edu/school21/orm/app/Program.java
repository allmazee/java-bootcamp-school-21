package edu.school21.orm.app;

import edu.school21.orm.models.User;
import edu.school21.orm.ormmanager.OrmManager;

import javax.sql.DataSource;

public class Program {
    public static void main(String[] args) {
        DataSource dataSource = DatabaseConnector.getDataSource();
        OrmManager manager = new OrmManager(dataSource);
        manager.init();
        User user = new User(1, "Alex", "Moralex",11);
        manager.save(user);
        System.out.println("Before update:\n" + manager.findById(1L, User.class));

        user.setFirstName("Jane");
        user.setAge(12);
        manager.update(user);
        System.out.println("After update:\n" + manager.findById(1L, User.class));
    }
}
