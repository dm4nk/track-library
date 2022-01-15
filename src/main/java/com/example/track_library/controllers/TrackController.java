package com.example.track_library.controllers;

import com.example.track_library.commands.GenreCommand;
import com.example.track_library.commands.TrackCommand;
import com.example.track_library.domain.Genre;
import com.example.track_library.service.GenreService;
import com.example.track_library.service.TrackService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/track")
public class TrackController {
    private final TrackService trackService;
    private final GenreService genreService;

    public TrackController(TrackService trackService, GenreService genreService) {
        this.trackService = trackService;
        this.genreService = genreService;
    }

    @GetMapping({"new/", "new"})
    public String newTrack(Model model) {
        model.addAttribute("track", TrackCommand.builder().genreCommand(GenreCommand.builder().build()).build());

        model.addAttribute("genreList", genreService.findAll());

        return "databaseDirectory/track/new";
    }

    @GetMapping({"", "/"})
    public String allTrack(Model model) {
        model.addAttribute("tracks", trackService.findAll());

        return "databaseDirectory/track/database";
    }

    @GetMapping({"{id}/update/", "{id}/update"})
    public String updateTrack(@PathVariable Integer id, Model model) {
        model.addAttribute("track", trackService.findCommandById(id));

        model.addAttribute("genreList", genreService.findAll());

        return "databaseDirectory/track/new";
    }

    @GetMapping({"{id}/delete/", "{id}/delete"})
    public String deleteTrack(@PathVariable Integer id) {
        trackService.deleteById(id);
        return "redirect:/track/";
    }

    @PostMapping({"saveOrUpdate/", "saveOrUpdate"})
    public String saveOrUpdateTrack(@ModelAttribute @Valid TrackCommand command, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("track", command);

            model.addAttribute("genreList", genreService.findAll());

            return "databaseDirectory/track/new";
        }

        TrackCommand savedCommand = trackService.saveTrackCommand(command);
        return "redirect:/track/";
    }

    @GetMapping({"/json/", "/json"})
    public @ResponseBody
    List<Genre> getTracksJson() {
        return genreService.findAll();
    }
}
