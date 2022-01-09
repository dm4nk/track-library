package com.example.netcracker_lab_2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @GetMapping
    @RequestMapping("/")
    public String init(Model model) {
        return "index";
    }
}
