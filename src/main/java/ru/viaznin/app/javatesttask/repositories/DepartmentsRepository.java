package ru.viaznin.app.javatesttask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.viaznin.app.javatesttask.models.Department;

/**
 * @author Ilya Viaznin
 */
public interface DepartmentsRepository extends JpaRepository<Department, Long> {
    default void fullDepartmentUpdate(Department department, long id){
        var updatingDepartment = findById(id).orElse(null);

        if(updatingDepartment == null)
            return;

        updatingDepartment.setName(department.getName());

        save(updatingDepartment);
    }
}
