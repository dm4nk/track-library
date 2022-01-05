package com.example.netcracker_lab_2.controllers;

import com.example.netcracker_lab_2.commands.GenreCommand;
import com.example.netcracker_lab_2.service.GenreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    @RequestMapping("genre/new/")
    public String newGenre(Model model) {
        model.addAttribute("genre", new GenreCommand());

        return "databaseDirectory/genre/new";
    }

    @GetMapping
    @RequestMapping("genre/")
    public String allGenres(Model model) {
        model.addAttribute("genres", genreService.findAll());

        return "databaseDirectory/genre/database";
    }

    @GetMapping
    @RequestMapping("genre/{id}/update/")
    public String updateGenre(@PathVariable String id, Model model) {
        model.addAttribute("genre", genreService.findCommandById(Integer.parseInt(id)));
        return "databaseDirectory/genre/new";
    }

    @GetMapping
    @RequestMapping("genre/{id}/delete/")
    public String deleteGenre(@PathVariable String id) {
        genreService.deleteById(Integer.parseInt(id));
        return "redirect:/genre/";
    }

    @PostMapping
    @RequestMapping("/genre/saveOrUpdate/")
    public String saveOrUpdateGenre(@ModelAttribute GenreCommand command) {
        GenreCommand savedCommand = genreService.saveGenreCommand(command);
        return "redirect:/genre/";
    }
}
