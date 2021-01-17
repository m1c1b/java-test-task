package ru.viaznin.app.javatesttask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.viaznin.app.javatesttask.models.User;

import java.util.List;

/**
 * @author Ilya Viaznin
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find departments by name substring
     *
     * @param searchName name substring
     * @return Departments
     */
    List<User> findByNameLikeIgnoreCaseOrderByName(String searchName);

    /**
     * Update all user fields
     *
     * @param user   Edited user model
     * @param userId Identifier of editing user
     */
    default void fullUpdate(User user, long userId) {
        var userFromDb = findById(userId).orElseThrow();

        userFromDb.setName(user.getName());
        userFromDb.setAge(user.getAge());
        userFromDb.setPhoneNumber(user.getPhoneNumber());
        userFromDb.setDepartment(user.getDepartment());

        save(userFromDb);
    }
}