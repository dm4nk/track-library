package com.example.netcracker_lab_2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {
    @RequestMapping({"/error/{message}", "/error/{message}"})
    public String error( @PathVariable String message, Model model){
        model.addAttribute("message", message);
        return "error";
    }
}
