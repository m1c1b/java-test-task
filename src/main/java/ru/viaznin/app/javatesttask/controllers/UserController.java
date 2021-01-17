package ru.viaznin.app.javatesttask.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.viaznin.app.javatesttask.models.User;
import ru.viaznin.app.javatesttask.repositories.DepartmentsRepository;
import ru.viaznin.app.javatesttask.repositories.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ilya Viaznin
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final DepartmentsRepository departmentsRepository;

    @Autowired
    public UserController(UserRepository userRepository, DepartmentsRepository departmentsRepository) {
        this.userRepository = userRepository;
        this.departmentsRepository = departmentsRepository;
    }

    @GetMapping
    public String index(@RequestParam(required = false) Long departmentId, @RequestParam(required = false) Long selectedUserId,
                        @RequestParam(required = false) String searchName, Model model) {
        List<User> userList = userRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(User::getName))
                .collect(Collectors.toList());

        if (departmentId != null)
            userList = userList
                    .stream()
                    .filter(u -> u.getDepartment() != null && u.getDepartment().getId() == departmentId)
                    .collect(Collectors.toList());

        // Search by all users
        if (searchName != null)
            userList = userRepository.findByNameLikeIgnoreCaseOrderByName("%" + searchName + "%");

        model.addAttribute("userList", userList);

        if (selectedUserId != null) {
            var selectedUser = userRepository.findById(selectedUserId).orElse(null);
            model.addAttribute("selectedUser", selectedUser);

        }

        var departments = departmentsRepository.findAll();
        model.addAttribute("departments", departments);
        model.addAttribute("newUser", new User());

        return "/user/index";
    }

    //TODO Must be PATCH method
    @SuppressWarnings("UnnecessaryLocalVariable")
    @PostMapping("/edit/{userId}")
    public String edit(@ModelAttribute("selectedUser") User user, @PathVariable long userId) {
        userRepository.update(user, userId);

        var indexWithParams = "redirect:/user?" + "selectedUserId=" + userId + "&" + "departmentId=" + user.getDepartment().getId();
        return indexWithParams;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("newUser") User newUser){
        userRepository.save(newUser);

        return "redirect:/user";
    }

    //TODO Must be DELETE method
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        userRepository.deleteById(id);

        return "redirect:/user";
    }
}