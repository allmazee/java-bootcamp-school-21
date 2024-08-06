package edu.school21.orm.models;

import edu.school21.orm.annotations.OrmColumn;
import edu.school21.orm.annotations.OrmColumnId;
import edu.school21.orm.annotations.OrmEntity;

import java.util.StringJoiner;

@OrmEntity(table = "simple_user")
public class User {
    @OrmColumnId
    private long id;
    @OrmColumn(name = "first_name", length = 10)
    private String firstName;
    @OrmColumn(name = "last_name", length = 10)
    private String lastName;
    @OrmColumn(name = "age")
    private Integer age;

    public User(long id, String firstName, String lastName, Integer age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public User() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("first_name='" + firstName + "'")
                .add("last_name='" + lastName + "'")
                .add("age='" + age + "'")
                .toString();
    }
}
