package com.example.track_library.service;

import com.example.track_library.commands.GenreCommand;
import com.example.track_library.domain.Genre;

import java.util.List;

/**
 * Service with crud operations with {@link Genre}
 */
public interface GenreService {
    List<Genre> findAll();

    List<Genre> findAllByNameLikeAlternative(String template);

    List<Genre> findAllByNameLike(String template);

    Genre findById(Integer id);

    GenreCommand saveGenreCommand(GenreCommand genreCommand);

    void deleteById(Integer id);

    GenreCommand findCommandById(Integer id);
}
