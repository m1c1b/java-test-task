package ru.viaznin.app.javatesttask.extensions;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Class for controller extensions
 *
 * @author Ilya Viaznin
 *
 */
public final class ControllerExtensions {
    public static void AddBindingResultErrorsToRedirectAttributes(BindingResult bindingResult, RedirectAttributes redirectAttributes, String attributeKey) {
        var mappedErrors = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(m -> m))
                .collect(Collectors.toList());

        var key = attributeKey == null || attributeKey.isEmpty()
                ? "errors"
                : attributeKey;

        redirectAttributes.addFlashAttribute(key, mappedErrors);
    }
}