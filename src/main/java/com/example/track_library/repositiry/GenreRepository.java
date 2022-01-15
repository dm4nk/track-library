package com.example.track_library.repositiry;

import com.example.track_library.domain.Genre;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Repository class for {@link Genre} domain objects
 */
@Repository
public interface GenreRepository extends CrudRepository<Genre, Integer> {
    @Transactional
    @CacheEvict(value = "genres", allEntries = true)
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
