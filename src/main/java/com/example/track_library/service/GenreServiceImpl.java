package com.example.track_library.service;

import com.example.track_library.commands.GenreCommand;
import com.example.track_library.converters.GenreCommandToGenre;
import com.example.track_library.converters.GenreToGenreCommand;
import com.example.track_library.domain.Genre;
import com.example.track_library.repositiry.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


/**
 * Service with crud operations with {@link Genre}
 */
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

    /**
     * Retrieve all {@link Genre}s from data store
     *
     * @return a Collection of {@link Genre}s
     */
    @Override
    public List<Genre> findAll() {
        List<Genre> genreList = new LinkedList<>();
        genreRepository.findAll().iterator().forEachRemaining(genreList::add);
        return genreList;
    }

    /**
     * Retrieve all {@link Genre}s from data source with Name matching template.
     * Same result as {@link GenreService#findAllByNameLike}, but using stream API
     *
     * @param template A template to compare to
     * @return Collection of {@link Genre}s
     */
    @Override
    public List<Genre> findAllByNameLikeAlternative(String template) {
        List<Genre> genreList = findAll();

        String lowerTemplate = template.toLowerCase(Locale.ROOT);

        return genreList.stream()
                .filter(genre -> genre.getName().toLowerCase(Locale.ROOT).contains(lowerTemplate))
                .collect(Collectors.toList());
    }

    /**
     * Retrieve all {@link Genre}s from data source with Name matching template.
     * Same result as {@link GenreService#findAllByNameLikeAlternative}, but using JPQL query
     *
     * @param template A template to compare to
     * @return Collection of {@link Genre}s
     */
    @Override
    public List<Genre> findAllByNameLike(String template) {
        return genreRepository.findAllByNameLike(template);
    }

    /**
     * Retrieve {@link Genre} with given id
     *
     * @param id Id to look for in data source
     * @return Single {@link Genre}
     */
    @Override
    public Genre findById(Integer id) {
        return genreRepository.findById(id).orElse(null);
    }

    /**
     * Save an {@link GenreCommand} to the data store, either inserting or updating it
     *
     * @param genreCommand {@link GenreCommand} to save
     * @return saved {@link GenreCommand} with id
     */
    @Override
    public GenreCommand saveGenreCommand(GenreCommand genreCommand) {
        Genre detachedGenre = genreCommandToGenre.convert(genreCommand);

        Genre savedGenre = genreRepository.save(detachedGenre);

        return genreToGenreCommand.convert(savedGenre);
    }

    /**
     * Deletes {@link Genre} with given id from data source
     *
     * @param id {@link Genre} with given id will be deleted
     */
    @Override
    public void deleteById(Integer id) {
        genreRepository.deleteById(id);
    }

    /**
     * Retrieve {@link GenreCommand} with given id
     *
     * @param id Id to look for in data source
     * @return Single {@link GenreCommand}
     */
    @Override
    public GenreCommand findCommandById(Integer id) {
        return genreToGenreCommand.convert(findById(id));
    }
}
