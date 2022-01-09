package com.example.netcracker_lab_2.controllers;

import com.example.netcracker_lab_2.commands.GenreCommand;
import com.example.netcracker_lab_2.commands.TrackCommand;
import com.example.netcracker_lab_2.service.GenreService;
import com.example.netcracker_lab_2.service.TrackService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TrackController {
    private final TrackService trackService;
    private final GenreService genreService;

    public TrackController(TrackService trackService, GenreService genreService) {
        this.trackService = trackService;
        this.genreService = genreService;
    }

    @GetMapping
    @RequestMapping("track/new/")
    public String newGenre(Model model) {
        model.addAttribute("track", TrackCommand.builder().genreCommand(GenreCommand.builder().build()).build());

        model.addAttribute("genreList", genreService.findAll());

        return "databaseDirectory/track/new";
    }

    @GetMapping
    @RequestMapping("track/")
    public String allGenres(Model model) {
        model.addAttribute("tracks", trackService.findAll());

        return "databaseDirectory/track/database";
    }

    @GetMapping
    @RequestMapping("track/{id}/update/")
    public String updateGenre(@PathVariable String id, Model model) {
        model.addAttribute("track", trackService.findCommandById(Integer.parseInt(id)));

        model.addAttribute("genreList", genreService.findAll());

        return "databaseDirectory/track/new";
    }

    @GetMapping
    @RequestMapping("track/{id}/delete/")
    public String deleteGenre(@PathVariable String id) {
        trackService.deleteById(Integer.parseInt(id));
        return "redirect:/track/";
    }

    @PostMapping
    @RequestMapping("/track/saveOrUpdate/")
    public String saveOrUpdateGenre(@ModelAttribute TrackCommand command) {
        TrackCommand savedCommand = trackService.saveTrackCommand(command);
        return "redirect:/track/";
    }
}
