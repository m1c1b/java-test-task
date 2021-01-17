package ru.viaznin.app.javatesttask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.viaznin.app.javatesttask.models.User;

import java.util.List;

/**
 * @author Ilya Viaznin
 */
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByNameLikeIgnoreCaseOrderByName(String searchName);

    default void update(User user, long userId){
        var userFromDb = findById(userId).orElseThrow();

        userFromDb.setName(user.getName());
        userFromDb.setAge(user.getAge());
        userFromDb.setPhoneNumber(user.getPhoneNumber());
        userFromDb.setDepartment(user.getDepartment());

        save(userFromDb);
    }
}