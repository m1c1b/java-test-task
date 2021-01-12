package ru.viaznin.app.javatesttask.models;

import javax.persistence.*;

/**
 * @author Ilya Viaznin
 */
@Entity
@Table(name = "t_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    private Department department;
}