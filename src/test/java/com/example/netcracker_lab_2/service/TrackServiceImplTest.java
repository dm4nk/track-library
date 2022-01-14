package com.example.netcracker_lab_2.service;

import com.example.netcracker_lab_2.commands.TrackCommand;
import com.example.netcracker_lab_2.converters.GenreCommandToGenre;
import com.example.netcracker_lab_2.converters.GenreToGenreCommand;
import com.example.netcracker_lab_2.converters.TrackCommandToTrack;
import com.example.netcracker_lab_2.converters.TrackToTrackCommand;
import com.example.netcracker_lab_2.domain.Genre;
import com.example.netcracker_lab_2.domain.Track;
import com.example.netcracker_lab_2.repositiry.GenreRepository;
import com.example.netcracker_lab_2.repositiry.TrackRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrackServiceImplTest {

    private final TrackToTrackCommand trackToTrackCommand;
    private final TrackCommandToTrack trackCommandToTrack;
    private final GenreToGenreCommand genreToGenreCommand;
    private final GenreCommandToGenre genreCommandToGenre;

    @Mock
    TrackRepository trackRepository;

    @Mock
    GenreRepository genreRepository;

    GenreService genreService;

    TrackServiceImpl trackService;

    Track track;
    Track track2;
    TrackCommand trackCommand;

    TrackServiceImplTest() {
        this.genreToGenreCommand = new GenreToGenreCommand();
        this.genreCommandToGenre = new GenreCommandToGenre();
        this.trackToTrackCommand = new TrackToTrackCommand(genreToGenreCommand);
        this.trackCommandToTrack = new TrackCommandToTrack(genreCommandToGenre);
    }


    @BeforeEach
    void setUp() {
        genreService = new GenreServiceImpl(genreRepository, genreToGenreCommand, genreCommandToGenre);
        trackService = new TrackServiceImpl(trackRepository, genreService, trackToTrackCommand, trackCommandToTrack);

        track = Track.builder().id(1)
                .name("name")
                .author("name")
                .album("name")
                .duration(1)
                .genre(
                        Genre.builder()
                                .name("name")
                                .build()
                )
                .build();

        track2 = Track.builder().id(1)
                .name("other name")
                .author("other name")
                .album("other name")
                .duration(1)
                .genre(
                        Genre.builder()
                                .name("other name")
                                .build()
                )
                .build();


        trackCommand = TrackCommand.builder().name("name").build();
    }

    @Test
    void findAll() {
        when(trackRepository.findAll()).thenReturn(Arrays.asList(
                Track.builder().id(1).build(),
                Track.builder().id(2).build()
        ));

        List<Track> genreList = trackService.findAll();

        Assertions.assertEquals(2, genreList.size());
        Assertions.assertEquals(1, genreList.get(0).getId());
        Assertions.assertEquals(2, genreList.get(1).getId());

        verify(trackRepository, times(1)).findAll();
    }

    @Test
    void findAllByNameAlbumAuthorLikeAlternative() {//Dizzy
        when(trackRepository.findAll()).thenReturn(List.of(track, track2));

        List<Track> trackList = trackService.findAllByNameAlbumAuthorLikeAlternative("name");
        Assertions.assertEquals(2, trackList.size());

        trackList = trackService.findAllByNameAlbumAuthorLikeAlternative("other name");
        Assertions.assertEquals(1, trackList.size());

        verify(trackRepository, times(2)).findAll();
    }

    @Test
    void findAllByNameLike() { //Dizzy
        when(trackRepository.findAllByNameAlbumAuthorLike(any())).thenReturn(List.of(track, track2));

        List<Track> genreList = trackService.findAllByNameAlbumAuthorLike(any());

        Assertions.assertEquals(2, genreList.size());
        Assertions.assertEquals("name", genreList.get(0).getName());
        Assertions.assertEquals("other name", genreList.get(1).getName());

        verify(trackRepository, times(1)).findAllByNameAlbumAuthorLike(any());
    }

    @Test
    void findById() {
        when(trackRepository.findById(1)).thenReturn(Optional.of(track));

        Track savedTrack = trackService.findById(1);
        Track nullTrack = trackService.findById(2);

        Assertions.assertEquals(1, savedTrack.getId());
        Assertions.assertNull(nullTrack);

        verify(trackRepository, times(2)).findById(anyInt());
    }

    @Test
    void saveGenreCommand() {
        when(trackRepository.save(any())).thenReturn(track);

        TrackCommand savedTrackCommand = trackService.saveTrackCommand(trackCommand);

        Assertions.assertEquals(trackCommand.getName(), savedTrackCommand.getName());

        verify(trackRepository, times(1)).save(any());
    }

    @Test
    void deleteById() {
        trackService.deleteById(anyInt());

        verify(trackRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void findCommandById() {
        when(trackRepository.findById(1)).thenReturn(Optional.of(track));
        when(trackRepository.findById(2)).thenReturn(Optional.empty());

        TrackCommand genreTrack = trackService.findCommandById(1);
        TrackCommand nullTrack = trackService.findCommandById(2);

        Assertions.assertEquals(1, genreTrack.getId());
        Assertions.assertNull(nullTrack);

        verify(trackRepository, times(2)).findById(anyInt());
    }
}