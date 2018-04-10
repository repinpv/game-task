package com.demo.gametask.utils;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.*;

@Component
public class FormErrorAdapter {

    private final MessageSource messageSource;

    public FormErrorAdapter(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void processErrors(BindingResult bindingResult, Model model) {

        final Map<String, List<String>> errorMap = new HashMap<>();
        for (final ObjectError error : bindingResult.getAllErrors()) {
            if (error instanceof FieldError) {

                FieldError fieldError = (FieldError) error;

                final String key = "error-" + fieldError.getField();
                final List<String> errorList = errorMap.computeIfAbsent(key, k -> new ArrayList<>());

                final String message = messageSource.getMessage(error, Locale.ENGLISH);
                errorList.add(message);
            }
        }

        model.addAllAttributes(errorMap);
    }
}
