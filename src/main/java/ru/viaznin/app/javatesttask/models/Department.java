package ru.viaznin.app.javatesttask.models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ilya Viaznin
 */
@Entity
@Table(name = "t_departments")
public class Department {
    //region Fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Department> childDepartments;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Department parentDepartment;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<User> users;

    //endregion

    //region Ctors

    public Department(String name, List<Department> childDepartments, List<User> users) {
        this(name, childDepartments);
        this.users = users;
    }

    public Department(String name) {
        this.name = name;
    }

    public Department(String name, List<Department> childDepartments) {
        this(name);
        this.childDepartments = childDepartments;
    }

    protected Department(){}

    //endregion

    //region Default setters and getters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Department> getChildDepartments() {
        return childDepartments;
    }

    public void setChildDepartments(List<Department> childDepartments) {
        this.childDepartments = childDepartments;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Department getParentDepartment() {
        return parentDepartment;
    }

    public void setParentDepartment(Department parentDepartment) {
        this.parentDepartment = parentDepartment;
    }

    //endregion

    public void addChild(Department child){
        child.setParentDepartment(this);

        if (childDepartments == null)
            childDepartments = new ArrayList<>();

        childDepartments.add(child);
    }
}