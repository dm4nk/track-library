package com.example.netcracker_lab_2.service;

import com.example.netcracker_lab_2.commands.GenreCommand;
import com.example.netcracker_lab_2.converters.GenreCommandToGenre;
import com.example.netcracker_lab_2.converters.GenreToGenreCommand;
import com.example.netcracker_lab_2.domain.Genre;
import com.example.netcracker_lab_2.repositiry.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final GenreToGenreCommand genreToGenreCommand;
    private final GenreCommandToGenre genreCommandToGenre;

    public GenreServiceImpl(GenreRepository genreRepository, GenreToGenreCommand genreToGenreCommand, GenreCommandToGenre genreCommandToGenre) {
        this.genreRepository = genreRepository;
        this.genreToGenreCommand = genreToGenreCommand;
        this.genreCommandToGenre = genreCommandToGenre;
    }


    @Override
    public List<Genre> findAll() {
        List<Genre> genreList = new LinkedList<>();
        genreRepository.findAll().iterator().forEachRemaining(genreList::add);
        return genreList;
    }

    @Override
    public List<Genre> findByTemplate(String template) {
        List<Genre> genreList = findAll();

        String lowerTemplate = template.toLowerCase(Locale.ROOT);

        return genreList.stream()
                .filter(genre -> genre.getName().toLowerCase(Locale.ROOT).contains(lowerTemplate))
                .collect(Collectors.toList());
    }

    @Override
    public Genre findById(Integer id) {
        return genreRepository.findById(id).orElse(null);
    }

    @Override
    public GenreCommand saveGenreCommand(GenreCommand genreCommand) {
        Genre detachedGenre = genreCommandToGenre.convert(genreCommand);

        Genre savedGenre = genreRepository.save(detachedGenre);

        return genreToGenreCommand.convert(savedGenre);
    }

    @Override
    public void deleteById(Integer id) {
        genreRepository.deleteById(id);
    }

    @Override
    public GenreCommand findCommandById(Integer id) {
        return genreToGenreCommand.convert(findById(id));
    }
}
