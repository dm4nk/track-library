package com.example.netcracker_lab_2.service;

import com.example.netcracker_lab_2.commands.GenreCommand;
import com.example.netcracker_lab_2.domain.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> findAll();

    List<Genre> findByTemplate(String template);

    Genre findById(Integer id);

    GenreCommand saveGenreCommand(GenreCommand genreCommand);

    void deleteById(Integer id);

    GenreCommand findCommandById(Integer id);
}
