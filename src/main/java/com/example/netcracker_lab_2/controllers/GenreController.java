package com.example.netcracker_lab_2.controllers;

import com.example.netcracker_lab_2.commands.GenreCommand;
import com.example.netcracker_lab_2.service.GenreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/genre")
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping({"new/", "new" })
    public String newGenre(Model model) {
        model.addAttribute("genre", GenreCommand.builder().build());

        return "databaseDirectory/genre/new";
    }

    @GetMapping({"", "/"})
    public String allGenres(Model model) {
        model.addAttribute("genres", genreService.findAll());

        return "databaseDirectory/genre/database";
    }

    @GetMapping({"{id}/update/", "{id}/update"})
    public String updateGenre(@PathVariable String id, Model model) {
        model.addAttribute("genre", genreService.findCommandById(Integer.parseInt(id)));
        return "databaseDirectory/genre/new";
    }

    @GetMapping({"{id}/delete/", "{id}/delete"})
    public String deleteGenre(@PathVariable String id) {
        genreService.deleteById(Integer.parseInt(id));
        return "redirect:/genre/";
    }

    @PostMapping({"saveOrUpdate/", "saveOrUpdate"})
    public String saveOrUpdateGenre(@ModelAttribute GenreCommand command) {
        GenreCommand savedCommand = genreService.saveGenreCommand(command);
        return "redirect:/genre/";
    }
}
