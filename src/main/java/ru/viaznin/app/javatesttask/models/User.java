package ru.viaznin.app.javatesttask.models;

import javax.persistence.*;

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
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "phone_number")
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
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