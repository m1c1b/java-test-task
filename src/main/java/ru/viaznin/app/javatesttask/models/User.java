package ru.viaznin.app.javatesttask.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * @author Ilya Viaznin
 */
@Entity
@Table(name = "t_users")
public class User {
    //region Fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    @Size(min = 2, message = "Username too short")
    private String name;

    @Column(name = "age")
    @Positive(message = "Age must be a positive number")
    @NotNull(message = "Age cannot be null")
    private Integer age;

    @Column(name = "phone_number")
    @NotNull(message = "Phone number cannot be null")
    @NotEmpty(message = "Phone number cannot be empty")
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    private Department department;

    //endregion

    //region Ctors

    public User() {
    }

    public User(String name, int age, String phoneNumber) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

    public User(String name, int age, String phoneNumber, Department department) {
        this(name, age, phoneNumber);
        this.department = department;
    }

    //endregion

    //region Default setters and getters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    //endregion


    @Override
    public String toString() {
        return "name:" + name + ", " + "age: " + age + ", " + "phone: " + phoneNumber;
    }
}