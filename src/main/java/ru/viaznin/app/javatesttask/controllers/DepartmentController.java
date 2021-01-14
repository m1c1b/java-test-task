package ru.viaznin.app.javatesttask.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.viaznin.app.javatesttask.repositories.DepartmentsRepository;

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

    @GetMapping("/get")
    public String get(Model model) {
        var departments = departmentsRepository.findAll()
                .stream()
                .filter(d -> d.getChildDepartments().size() > 0)
                .collect(Collectors.toList());

        model.addAttribute("departments", departments);

        return "department/departmentsTree";
    }
}
