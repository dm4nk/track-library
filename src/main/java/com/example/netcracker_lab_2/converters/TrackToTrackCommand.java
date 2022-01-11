package com.example.netcracker_lab_2.converters;

import com.example.netcracker_lab_2.commands.TrackCommand;
import com.example.netcracker_lab_2.domain.Track;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converter class
 */
@Component
public class TrackToTrackCommand implements Converter<Track, TrackCommand> {
    private final GenreToGenreCommand genreToGenreCommand;

    public TrackToTrackCommand(GenreToGenreCommand genreToGenreCommand) {
        this.genreToGenreCommand = genreToGenreCommand;
    }

    /**
     * Converts {@link Track} to {@link TrackCommand}
     */
    @Override
    public TrackCommand convert(Track source) {
        return TrackCommand.builder()
                .id(source.getId())
                .name(source.getName())
                .author(source.getAuthor())
                .album(source.getAlbum())
                .duration(source.getDuration())
                .genreCommand(genreToGenreCommand.convert(source.getGenre()))
                .build();
    }
}
