package ru.viaznin.app.javatesttask.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.viaznin.app.javatesttask.extensions.ControllerExtensions;
import ru.viaznin.app.javatesttask.models.Department;
import ru.viaznin.app.javatesttask.repositories.DepartmentsRepository;

import javax.validation.Valid;
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
        model.addAttribute("newDepartment", new Department());

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

    //TODO Must be PATCH method
    @PostMapping("/edit/{id}")
    public String edit(@ModelAttribute("selectedDepartment") @Valid Department department, BindingResult bindingResult, @PathVariable long id, final RedirectAttributes redirectAttributes) {
        var indexWithParams = "redirect:/department?selectedDepartmentId=" + id;

        if (bindingResult.hasErrors()) {
            ControllerExtensions
                    .AddBindingResultErrorsToRedirectAttributes(bindingResult, redirectAttributes, "editErrors");

            return indexWithParams;
        }

        departmentsRepository.fullDepartmentUpdate(department, id);

        return indexWithParams;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("newDepartment") @Valid Department newDepartment, BindingResult bindingResult, @RequestParam(required = false) Long parentId, final RedirectAttributes redirectAttributes) {
        var index = "redirect:/department";

        if (bindingResult.hasErrors()) {
            ControllerExtensions
                    .AddBindingResultErrorsToRedirectAttributes(bindingResult, redirectAttributes, "createErrors");

            return index;
        }

        departmentsRepository.create(newDepartment, parentId);

        return index;
    }

    //TODO Must be DELETE method
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        departmentsRepository.deleteDepartmentById(id);

        return "redirect:/department";
    }
}
