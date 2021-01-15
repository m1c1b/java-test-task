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

    default void create(Department newDepartment, Long parentId){
        if(parentId == null) {
            save(newDepartment);
            return;
        }

        var parentDepartment = findById(parentId).orElseThrow();

        parentDepartment.addChild(newDepartment);

        save(parentDepartment);
    }

    default void deleteDepartmentById(long id){
        var department = findById(id).orElseThrow();

        if(department.getParentDepartment() != null)
            department.getParentDepartment().getChildDepartments().removeIf(d -> d.getId() == id);
        department.setParentDepartment(null);

        delete(department);
    }
}
