package com.example.track_library.commands;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Transfers data between view and model
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrackCommand {
    Integer id;
    @NotEmpty
    String name;
    @NotEmpty
    String author;
    @NotEmpty
    String album;
    @Digits(fraction = 0, integer = 6)
    @NotNull
    Integer duration;
    GenreCommand genreCommand;
}
