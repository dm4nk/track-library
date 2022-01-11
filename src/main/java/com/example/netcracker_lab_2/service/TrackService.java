package com.example.netcracker_lab_2.service;

import com.example.netcracker_lab_2.commands.TrackCommand;
import com.example.netcracker_lab_2.domain.Track;

import java.util.List;

/**
 * Service with crud operations with {@link Track}
 */
public interface TrackService {
    List<Track> findAll();

    List<Track> findAllByNameAlbumAuthorLikeAlternative(String template);

    List<Track> findAllByNameAlbumAuthorLike(String template);

    Track findById(Integer id);

    TrackCommand saveTrackCommand(TrackCommand trackCommand);

    void deleteById(Integer id);

    TrackCommand findCommandById(Integer id);
}
