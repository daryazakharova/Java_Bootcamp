package model;

import annotations.OrmColumn;
import annotations.OrmColumnId;
import annotations.OrmEntity;

import java.util.StringJoiner;

@OrmEntity(table = "simple_user")
    public class User {
    @OrmColumnId
    private Long id;

    @OrmColumn(name = "first_name", length = 10)
    private String firstName;

    @OrmColumn(name = "last_name", length = 10)
    private String lastName;

    @OrmColumn(name = "age")
    private Integer age;

    public User(){};
    public User(Long id, String firstName, String lastName, Integer age){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
                .add("id=" + id)
                .add("firstName="+firstName)
                .add("lastName="+lastName)
                .add("age="+age)
                .toString();
    }
}

