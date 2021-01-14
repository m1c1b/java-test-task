package ru.viaznin.app.javatesttask.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.viaznin.app.javatesttask.models.Department;
import ru.viaznin.app.javatesttask.repositories.DepartmentsRepository;

import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * @author Ilya Viaznin
 */
@Controller
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentsRepository departmentsRepository;

    @Autowired
    public DepartmentController(DepartmentsRepository departmentsRepository) {
        this.departmentsRepository = departmentsRepository;
    }

    @GetMapping
    public String index(@RequestParam(required = false) Integer selectedDepartmentId, Model model) {
        var departments = departmentsRepository.findAll();

        var rootDepartments = departments
                .stream()
                .filter(d -> d.getParentDepartment() == null)
                .sorted(Comparator.comparingLong(Department::getId))
                .collect(Collectors.toList());

        model.addAttribute("rootDepartments", rootDepartments);

        if (selectedDepartmentId != null) {
            var selectedDepartment = departments
                    .stream()
                    .filter(d -> d.getId() == selectedDepartmentId)
                    .findFirst()
                    .orElseThrow();

            model.addAttribute("selectedDepartment", selectedDepartment);
        }

        return "department/index";
    }

    @PostMapping("/edit/{id}")
    public String edit(@ModelAttribute("selectedDepartment") Department department, @PathVariable long id) {
        departmentsRepository.fullDepartmentUpdate(department, id);

        return "redirect:/department?selectedDepartmentId=" + id;
    }
}
