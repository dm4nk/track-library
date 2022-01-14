package com.example.track_library.converters;

import com.example.track_library.commands.GenreCommand;
import com.example.track_library.domain.Genre;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converter class
 */
@Component
public class GenreCommandToGenre implements Converter<GenreCommand, Genre> {

    /**
     * Converts {@link GenreCommand} to {@link Genre}
     */
    @Override
    public Genre convert(GenreCommand source) {
        return source == null ? null : Genre.builder()
                .id(source.getId())
                .name(source.getName())
                .build();
    }
}
