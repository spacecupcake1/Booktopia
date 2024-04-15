package com.m295.booktopia;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping("/")
    public String home() {
        return "redirect:/swagger-ui.html";
    }
}