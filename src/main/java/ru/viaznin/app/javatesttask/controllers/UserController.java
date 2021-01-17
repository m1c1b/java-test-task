package ru.viaznin.app.javatesttask.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.viaznin.app.javatesttask.extensions.ControllerExtensions;
import ru.viaznin.app.javatesttask.models.User;
import ru.viaznin.app.javatesttask.repositories.DepartmentsRepository;
import ru.viaznin.app.javatesttask.repositories.UserRepository;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for interacting with users
 *
 * @author Ilya Viaznin
 */
@Controller
@RequestMapping("/user")
public class UserController {
    /**
     * User repository
     */
    private final UserRepository userRepository;
    /**
     * Department repository
     */
    private final DepartmentsRepository departmentsRepository;

    @Autowired
    public UserController(UserRepository userRepository, DepartmentsRepository departmentsRepository) {
        this.userRepository = userRepository;
        this.departmentsRepository = departmentsRepository;
    }

    /**
     * Get index page
     *
     * @param departmentId   Identifier of department
     * @param selectedUserId Identifier of selected user
     * @param searchName     String for searching users by name
     * @param model          Model
     * @return Index page
     */
    @GetMapping
    public String index(@RequestParam(required = false) Long departmentId, @RequestParam(required = false) Long selectedUserId,
                        @RequestParam(required = false) String searchName, final Model model) {
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

    /**
     * Edit user
     *
     * @param user               Edited user model
     * @param bindingResult      Validation result
     * @param userId             Identifier of editing user
     * @param redirectAttributes Attributes for sending params between views
     * @return Index page with all users of department and selected edited user
     */
    @PatchMapping("/edit/{userId}")
    @SuppressWarnings("SpringMVCViewInspection")
    public String edit(@ModelAttribute("selectedUser") @Valid User user, BindingResult bindingResult, @PathVariable long userId, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasErrors())
            userRepository.update(user, userId);

        ControllerExtensions
                .AddBindingResultErrorsToRedirectAttributes(bindingResult, redirectAttributes, null);

        return "redirect:/user?" + "selectedUserId=" + userId + "&" + "departmentId=" + user.getDepartment().getId();
    }

    /**
     * Create new user
     *
     * @param newUser            New user model
     * @param bindingResult      Validation result
     * @param redirectAttributes Attributes for sending params between views
     * @return Index page with all users of department
     */
    @PostMapping("/create")
    @SuppressWarnings("SpringMVCViewInspection")
    public String create(@ModelAttribute("newUser") @Valid User newUser, BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasErrors())
            userRepository.save(newUser);

        ControllerExtensions
                .AddBindingResultErrorsToRedirectAttributes(bindingResult, redirectAttributes, null);

        return "redirect:/user?" + "departmentId=" + newUser.getDepartment().getId();
    }

    /**
     * Delete user by identifier
     *
     * @param id Identifier of deleting user
     * @return Index page with all users
     */
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        userRepository.deleteById(id);

        return "redirect:/user";
    }
}