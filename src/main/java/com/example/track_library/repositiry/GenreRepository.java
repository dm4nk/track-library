package com.example.track_library.repositiry;

import com.example.track_library.domain.Genre;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import java.util.Collection;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;

/**
 * Repository class for {@link Genre} domain objects
 */
@Repository
public interface GenreRepository extends CrudRepository<Genre, Integer> {
    @Transactional
    @Cacheable("genres")
    Collection<Genre> findAll() throws DataAccessException;

    /**
     * Retrieve {@link Genre} from data store with given name
     *
     * @param name Value to search for
     * @return Single optional of genre with matching name (as name is unique column in {@link Genre})
     */
    @Query("select g from Genre g where g.name = ?1")
    Optional<Genre> findByName(String name);

    //todo: SQL инъекция
    @Query("select g from Genre g where g.name like %?1%")
    List<Genre> findAllByNameLike(String template);
}
