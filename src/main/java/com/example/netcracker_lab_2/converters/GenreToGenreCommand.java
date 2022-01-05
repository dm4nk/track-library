package com.example.netcracker_lab_2.converters;

import com.example.netcracker_lab_2.commands.GenreCommand;
import com.example.netcracker_lab_2.domain.Genre;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GenreToGenreCommand implements Converter<Genre, GenreCommand> {

    @Override
    public GenreCommand convert(@NonNull Genre source) {
        return GenreCommand.builder()
                .id(source.getId())
                .name(source.getName())
                .build();
    }
}
