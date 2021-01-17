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
 * Controller for interacting with departments
 *
 * @author Ilya Viaznin
 */
@Controller
@RequestMapping("/department")
public class DepartmentController {
    /**
     * Department repository
     */
    private final DepartmentsRepository departmentsRepository;

    @Autowired
    public DepartmentController(DepartmentsRepository departmentsRepository) {
        this.departmentsRepository = departmentsRepository;
    }

    /**
     * Get index page
     *
     * @param selectedDepartmentId Identifier of selected department
     * @param model                Model
     * @return Index page
     */
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

    /**
     * Edit department
     *
     * @param department         Edited department model
     * @param bindingResult      Validation result
     * @param id                 Identifier of editing department
     * @param redirectAttributes Attributes for sending params between views
     * @return Index page with edited department
     */
    @PatchMapping("/edit/{id}")
    public String edit(@ModelAttribute("selectedDepartment") @Valid Department department, BindingResult bindingResult, @PathVariable long id, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasErrors())
            departmentsRepository.fullDepartmentUpdate(department, id);

        ControllerExtensions
                .AddBindingResultErrorsToRedirectAttributes(bindingResult, redirectAttributes, "editErrors");

        return "redirect:/department?selectedDepartmentId=" + id;
    }

    /**
     * Create new department
     *
     * @param newDepartment      New department model
     * @param bindingResult      Validation result
     * @param parentId           Identifier of parent department
     * @param redirectAttributes Attributes for sending params between views
     * @return Index page or index page with selected parent department
     */
    @PostMapping("/create")
    public String create(@ModelAttribute("newDepartment") @Valid Department newDepartment, BindingResult bindingResult, @RequestParam(required = false) Long parentId, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasErrors())
            departmentsRepository.create(newDepartment, parentId);

        ControllerExtensions
                .AddBindingResultErrorsToRedirectAttributes(bindingResult, redirectAttributes, "createErrors");

        var index = "redirect:/department";

        return parentId == null ? index : index + "?selectedDepartmentId=" + parentId;
    }

    /**
     * Delete department by identifier
     *
     * @param id Identifier of deleting department
     * @return Index page
     */
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        departmentsRepository.deleteDepartmentById(id);

        return "redirect:/department";
    }
}
