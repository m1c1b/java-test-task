package ru.viaznin.app.javatesttask.initializers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.viaznin.app.javatesttask.models.Department;
import ru.viaznin.app.javatesttask.models.User;
import ru.viaznin.app.javatesttask.repositories.DepartmentsRepository;
import ru.viaznin.app.javatesttask.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Ilya Viaznin
 */
@Component
public class DatabaseInitializer implements ApplicationRunner {
    private final DepartmentsRepository departmentsRepository;
    private final UserRepository userRepository;

    @Autowired
    public DatabaseInitializer(DepartmentsRepository departmentsRepository, UserRepository userRepository) {
        this.departmentsRepository = departmentsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        initDepartments();
        initUsers();
    }

    private void initDepartments() {
        var departments = departmentsRepository.findAll();
        if (departments.size() > 0)
            return;

        var firstRoot = new Department("Department 1");
        var departmentOneOne = new Department("Department 1.1");
        departmentOneOne.addChild(new Department("Department 1.1.1"));
        firstRoot.addChild(departmentOneOne);
        firstRoot.addChild(new Department("Department 1.2"));

        var secondRoot = new Department("Department 2");
        var departmentTwoOne = new Department("Department 2.1");
        departmentTwoOne.addChild(new Department("Department 2.1.1"));
        secondRoot.addChild(departmentTwoOne);
        secondRoot.addChild(new Department("Department 2.2"));

        var newDepartments = new ArrayList<>(Arrays.asList(firstRoot, secondRoot));

        departmentsRepository.saveAll(newDepartments);
    }

    private void initUsers() {
        var users = userRepository.findAll();
        if (users.size() > 0)
            return;

        var namesList = new String[]{"Sam", "Mark", "Jeff", "Tom", "Rob", "Bob"};
        List<User> newUsers = new ArrayList<>();

        var departments = departmentsRepository.findAll();
        var random = new Random();
        var usersInDepartment = 3;

        departments.forEach(d -> {
            for (int i = 0; i < usersInDepartment; i++) {
                var randomName = namesList[random.nextInt(namesList.length - 1)];

                var randomPhoneNumber = (Integer) random.nextInt(Integer.MAX_VALUE);

                newUsers.add(new User(randomName, random.nextInt(100) + 1, randomPhoneNumber.toString(), d));
            }
        });

        userRepository.saveAll(newUsers);
    }
}
