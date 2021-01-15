package ru.viaznin.app.javatesttask.initializers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.viaznin.app.javatesttask.models.Department;
import ru.viaznin.app.javatesttask.repositories.DepartmentsRepository;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Ilya Viaznin
 */
@Component
public class DatabaseInitializer implements ApplicationRunner {
    private final DepartmentsRepository departmentsRepository;

    @Autowired
    public DatabaseInitializer(DepartmentsRepository departmentsRepository) {
        this.departmentsRepository = departmentsRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
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
}
