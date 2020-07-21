package com.devculi.sway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    static {
    System.out.println("Scanned");
    }
    @GetMapping
    public String index() {
        return "index";
    }
}
