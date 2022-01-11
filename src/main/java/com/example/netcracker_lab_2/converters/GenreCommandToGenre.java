package com.example.netcracker_lab_2.converters;

import com.example.netcracker_lab_2.commands.GenreCommand;
import com.example.netcracker_lab_2.domain.Genre;
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
        return Genre.builder()
                .id(source.getId())
                .name(source.getName())
                .build();
    }
}
