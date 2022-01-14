package com.example.track_library.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Simple business object representing a Track
 */
@Entity

@Getter
@Setter
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Track implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    String author;
    String album;
    Integer duration;
    @ManyToOne
    Genre genre;

    @Builder
    public Track(Integer id, String name, String author, String album, Integer duration, Genre genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.album = album;
        this.duration = duration;
        this.genre = genre;
    }
}
