package com.example.netcracker_lab_2.controllers;

import com.example.netcracker_lab_2.commands.GenreCommand;
import com.example.netcracker_lab_2.domain.Genre;
import com.example.netcracker_lab_2.service.GenreService;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class GenreControllerTest {

    @Mock
    GenreService genreService;

    @InjectMocks
    GenreController genreController;

    MockMvc mockMvc;

    GenreCommand genreCommand;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(genreController).build();

        genreCommand = GenreCommand.builder().id(1).build();
    }

    @Test
    void newGenre() throws Exception {
        mockMvc.perform(get("/genre/new/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("genre"))
                .andExpect(view().name("databaseDirectory/genre/new"));
    }

    @Test
    void allGenres() throws Exception {
        when(genreService.findAll()).thenReturn(Arrays.asList(
                Genre.builder().id(1).build(),
                Genre.builder().id(2).build()
        ));

        mockMvc.perform(get("/genre/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("genres"))
                .andExpect(view().name("databaseDirectory/genre/database"));

        verify(genreService, times(1)).findAll();
    }

    @Test
    void updateGenre() throws Exception {
        when(genreService.findCommandById(any())).thenReturn(genreCommand);

        mockMvc.perform(get("/genre/1/update/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("genre"))
                .andExpect(view().name("databaseDirectory/genre/new"));

        verify(genreService, times(1)).findCommandById(any());
    }

    @Test
    void deleteGenre() throws Exception {
        mockMvc.perform(get("/genre/1/delete/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/genre/"));

        verify(genreService, times(1)).deleteById(any());
    }

    @Test
    void saveOrUpdateGenre() throws Exception {
        when(genreService.saveGenreCommand(any())).thenReturn(genreCommand);

        mockMvc.perform(post("/genre/saveOrUpdate/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/genre/"));

        verify(genreService, times(1)).saveGenreCommand(any());
    }
}