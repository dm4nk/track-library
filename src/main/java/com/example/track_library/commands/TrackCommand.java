package com.example.track_library.commands;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
    String name;
    String author;
    String album;
    Integer duration;
    GenreCommand genreCommand;
}
