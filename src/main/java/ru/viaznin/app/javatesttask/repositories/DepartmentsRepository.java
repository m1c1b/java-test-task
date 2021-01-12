package ru.viaznin.app.javatesttask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.viaznin.app.javatesttask.models.Department;

/**
 * @author Ilya Viaznin
 */
public interface DepartmentsRepository extends JpaRepository<Department, Long> {
}
