package com.example.netcracker_lab_2.service;

import com.example.netcracker_lab_2.commands.GenreCommand;
import com.example.netcracker_lab_2.converters.GenreCommandToGenre;
import com.example.netcracker_lab_2.converters.GenreToGenreCommand;
import com.example.netcracker_lab_2.domain.Genre;
import com.example.netcracker_lab_2.repositiry.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GenreServiceImplTest {

    private final GenreToGenreCommand genreToGenreCommand;
    private final GenreCommandToGenre genreCommandToGenre;

    @Mock
    GenreRepository genreRepository;

    GenreService genreService;

    Genre genre;
    GenreCommand genreCommand;

    GenreServiceImplTest() {
        this.genreToGenreCommand = new GenreToGenreCommand();
        this.genreCommandToGenre = new GenreCommandToGenre();
    }


    @BeforeEach
    void setUp() {
        genreService = new GenreServiceImpl(genreRepository, genreToGenreCommand, genreCommandToGenre);

        genre = Genre.builder().id(1).name("name").build();
        genreCommand = GenreCommand.builder().name("name").build();
    }

    @Test
    void findAll() {
        when(genreRepository.findAll()).thenReturn(Arrays.asList(
                Genre.builder().id(1).build(),
                Genre.builder().id(2).build()
        ));

        List<Genre> genreList = genreService.findAll();

        Assertions.assertEquals(2, genreList.size());
        Assertions.assertEquals(1, genreList.get(0).getId());
        Assertions.assertEquals(2, genreList.get(1).getId());

        verify(genreRepository, times(1)).findAll();
    }

    @Test
    void findAllByNameLikeAlternative() {//Dizzy
        when(genreRepository.findAll()).thenReturn(List.of(
                Genre.builder().id(1).name("name").build(),
                Genre.builder().id(2).name("other name").build()));

        List<Genre> genreList = genreService.findAllByNameLikeAlternative("name");
        Assertions.assertEquals(2, genreList.size());

        genreList = genreService.findAllByNameLikeAlternative("other name");
        Assertions.assertEquals(1, genreList.size());

        verify(genreRepository, times(2)).findAll();
    }

    @Test
    void findAllByNameLike() { //Dizzy
        when(genreRepository.findAllByNameLike(any())).thenReturn(List.of(
                Genre.builder().name("name").build(),
                Genre.builder().name("other name").build()
        ));

        List<Genre> genreList = genreService.findAllByNameLike(any());

        Assertions.assertEquals(2, genreList.size());
        Assertions.assertEquals("name", genreList.get(0).getName());
        Assertions.assertEquals("other name", genreList.get(1).getName());

        verify(genreRepository, times(1)).findAllByNameLike(any());
    }

    @Test
    void findById() {
        when(genreRepository.findById(1)).thenReturn(Optional.of(genre));

        Genre savedGenre = genreService.findById(1);
        Genre nullGenre = genreService.findById(2);

        Assertions.assertEquals(1, savedGenre.getId());
        Assertions.assertNull(nullGenre);

        verify(genreRepository, times(2)).findById(anyInt());
    }

    @Test
    void saveGenreCommand() {
        when(genreRepository.save(any())).thenReturn(genre);

        GenreCommand savedGenreCommand = genreService.saveGenreCommand(genreCommand);

        Assertions.assertEquals(genreCommand.getName(), savedGenreCommand.getName());

        verify(genreRepository, times(1)).save(any());
    }

    @Test
    void deleteById() {
        genreService.deleteById(anyInt());

        verify(genreRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void findCommandById() {
        when(genreRepository.findById(1)).thenReturn(Optional.of(genre));
        when(genreRepository.findById(2)).thenReturn(Optional.empty());

        GenreCommand genreCommand = genreService.findCommandById(1);
        GenreCommand nullGenre = genreService.findCommandById(2);

        Assertions.assertEquals(1, genreCommand.getId());
        Assertions.assertNull(nullGenre);

        verify(genreRepository, times(2)).findById(anyInt());
    }
}