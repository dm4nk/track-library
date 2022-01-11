package com.example.netcracker_lab_2.service;

import com.example.netcracker_lab_2.commands.TrackCommand;
import com.example.netcracker_lab_2.converters.TrackCommandToTrack;
import com.example.netcracker_lab_2.converters.TrackToTrackCommand;
import com.example.netcracker_lab_2.domain.Track;
import com.example.netcracker_lab_2.repositiry.TrackRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Service with crud operations with {@link Track}
 */
@Service
public class TrackServiceImpl implements TrackService {
    private final TrackRepository trackRepository;
    private final GenreService genreService;
    private final TrackToTrackCommand trackToTrackCommand;
    private final TrackCommandToTrack trackCommandToTrack;

    public TrackServiceImpl(TrackRepository trackRepository, GenreService genreService, TrackToTrackCommand trackToTrackCommand, TrackCommandToTrack trackCommandToTrack) {
        this.trackRepository = trackRepository;
        this.genreService = genreService;
        this.trackToTrackCommand = trackToTrackCommand;
        this.trackCommandToTrack = trackCommandToTrack;
    }

    /**
     * Retrieve all {@link Track}s from data store
     *
     * @return a Collection of {@link Track}s
     */
    @Override
    public List<Track> findAll() {
        List<Track> trackList = new LinkedList<>();
        trackRepository.findAll().iterator().forEachRemaining(trackList::add);
        return trackList;
    }

    /**
     * Retrieve all {@link Track}s from data source with Name or Author or Album or Genre matching template.
     * Same result as {@link TrackService#findAllByNameAlbumAuthorLike}, but using stream API
     *
     * @param template A template to compare to
     * @return Collection of {@link Track}s
     */
    @Override
    public List<Track> findAllByNameAlbumAuthorLikeAlternative(String template) {
        List<Track> trackList = findAll();

        String lowerTemplate = template.toLowerCase(Locale.ROOT);

        return trackList.stream()
                .filter(track -> track.getName().toLowerCase(Locale.ROOT).contains(lowerTemplate) ||
                        track.getAuthor().toLowerCase(Locale.ROOT).contains(lowerTemplate) ||
                        track.getAlbum().toLowerCase(Locale.ROOT).contains(lowerTemplate) ||
                        track.getGenre().getName().toLowerCase(Locale.ROOT).contains(lowerTemplate))
                .collect(Collectors.toList());
    }

    /**
     * Retrieve all {@link Track}s from data source with Name or Author or Album or Genre matching template.
     * Same result as {@link TrackService#findAllByNameAlbumAuthorLikeAlternative(String)}, but using JPQL query
     *
     * @param template A template to compare to
     * @return Collection of {@link Track}s
     */
    @Override
    public List<Track> findAllByNameAlbumAuthorLike(String template) {
        return trackRepository.findAllByNameAlbumAuthorLike(template);
    }

    /**
     * Retrieve {@link Track} with given id
     *
     * @param id Id to look for in data source
     * @return Single {@link Track}
     */
    @Override
    public Track findById(Integer id) {
        return trackRepository.findById(id).orElse(null);
    }

    /**
     * Save an {@link TrackCommand} to the data store, either inserting or updating it
     *
     * @param trackCommand {@link TrackCommand} to save
     * @return saved {@link TrackCommand} with id
     */
    @Override
    public TrackCommand saveTrackCommand(TrackCommand trackCommand) {
        Track detachedTrack = trackCommandToTrack.convert(trackCommand);

        Track savedTrack = trackRepository.save(detachedTrack);

        return trackToTrackCommand.convert(savedTrack);
    }

    /**
     * Deletes {@link Track} with given id from data source
     *
     * @param id {@link Track} with matching id will be deleted
     */
    @Override
    public void deleteById(Integer id) {
        trackRepository.deleteById(id);
    }

    /**
     * Retrieve {@link TrackCommand} with given id
     *
     * @param id Id to look for in data source
     * @return Single {@link TrackCommand}
     */
    @Override
    public TrackCommand findCommandById(Integer id) {
        return trackToTrackCommand.convert(findById(id));
    }
}
