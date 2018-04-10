package com.demo.gametask.layout;

import com.samskivert.mustache.Mustache;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class LayoutAdvice {
    @ModelAttribute("layout")
    public Mustache.Lambda layout() {
        return new Layout();
    }
}
