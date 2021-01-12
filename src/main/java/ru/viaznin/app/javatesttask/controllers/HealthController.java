package ru.viaznin.app.javatesttask.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * @author Ilya Viaznin
 */
@Controller
@RequestMapping("/health")
public class HealthController {
    /**
     * Get view to ensure that app work well
     *
     * @return Health check view
     */
    @GetMapping("/imOk")
    public String imOk(Model model) {
        var currentDateTime = new Date();
        model.addAttribute("time", currentDateTime);

        return "health/imOk";
    }
}
