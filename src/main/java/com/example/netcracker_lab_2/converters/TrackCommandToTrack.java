package com.example.netcracker_lab_2.converters;

import com.example.netcracker_lab_2.commands.TrackCommand;
import com.example.netcracker_lab_2.domain.Track;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converter class
 */
@Component
public class TrackCommandToTrack implements Converter<TrackCommand, Track> {
    private final GenreCommandToGenre genreCommandToGenre;

    public TrackCommandToTrack(GenreCommandToGenre genreCommandToGenre) {
        this.genreCommandToGenre = genreCommandToGenre;
    }

    /**
     * Converts {@link TrackCommand} to {@link Track}
     */
    @Override
    public Track convert(TrackCommand source) {
        return source == null ? null : Track.builder()
                .id(source.getId())
                .name(source.getName())
                .author(source.getAuthor())
                .album(source.getAlbum())
                .duration(source.getDuration())
                .genre(genreCommandToGenre.convert(source.getGenreCommand()))
                .build();
    }
}
