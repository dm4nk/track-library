package com.example.track_library.repositiry;

import com.example.track_library.domain.Track;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository class for {@link Track} domain objects
 */
@Repository
public interface TrackRepository extends CrudRepository<Track, Integer> {
    @Query("select t from Track t left join t.genre where t.name like %?1% or " +
            "t.album like %?1% or " +
            "t.author like %?1% or " +
            "t.genre.name like %?1%")
    List<Track> findAllByNameAlbumAuthorLike(String template);
}
