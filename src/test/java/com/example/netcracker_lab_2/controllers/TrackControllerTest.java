package com.example.netcracker_lab_2.controllers;

import com.example.netcracker_lab_2.commands.TrackCommand;
import com.example.netcracker_lab_2.domain.Genre;
import com.example.netcracker_lab_2.domain.Track;
import com.example.netcracker_lab_2.service.GenreService;
import com.example.netcracker_lab_2.service.TrackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TrackControllerTest {

    @Mock
    TrackService trackService;

    @Mock
    GenreService genreService;

    @InjectMocks
    TrackController trackController;

    MockMvc mockMvc;

    TrackCommand trackCommand;
    Genre genre;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(trackController).build();

        trackCommand = TrackCommand.builder().id(1).build();
        genre = Genre.builder().id(1).build();
    }

    @Test
    void newTrack() throws Exception {
        when(genreService.findAll()).thenReturn(List.of(genre));
        mockMvc.perform(get("/track/new/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("track"))
                .andExpect(model().attributeExists("genreList"))
                .andExpect(view().name("databaseDirectory/track/new"));
    }

    @Test
    void allTrack() throws Exception {
        when(trackService.findAll()).thenReturn(Arrays.asList(
                Track.builder().id(1).build(),
                Track.builder().id(2).build()
        ));

        mockMvc.perform(get("/track/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("tracks"))
                .andExpect(view().name("databaseDirectory/track/database"));

        verify(trackService, times(1)).findAll();
    }

    @Test
    void updateTrack() throws Exception {
        when(trackService.findCommandById(any())).thenReturn(trackCommand);
        when(genreService.findAll()).thenReturn(List.of(genre));

        mockMvc.perform(get("/track/1/update/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("track"))
                .andExpect(model().attributeExists("genreList"))
                .andExpect(view().name("databaseDirectory/track/new"));

        verify(trackService, times(1)).findCommandById(any());
    }

    @Test
    void deleteTrack() throws Exception {
        mockMvc.perform(get("/track/1/delete/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/track/"));

        verify(trackService, times(1)).deleteById(any());
    }

    @Test
    void saveOrUpdateTrack() throws Exception {
        when(trackService.saveTrackCommand(any())).thenReturn(trackCommand);

        mockMvc.perform(post("/track/saveOrUpdate/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/track/"));

        verify(trackService, times(1)).saveTrackCommand(any());
    }
}