package com.example.netcracker_lab_2.repositiry;

import com.example.netcracker_lab_2.domain.Genre;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository class for {@link Genre} domain objects
 */
@Repository
public interface GenreRepository extends CrudRepository<Genre, Integer> {
    /**
     * Retrieve {@link Genre} from data store with given name
     *
     * @param name Value to search for
     * @return Single optional of genre with matching name (as name is unique column in {@link Genre})
     */
    @Query("select g from Genre g where g.name = ?1")
    Optional<Genre> findByName(String name);

    @Query("select g from Genre g where g.name like %?1%")
    List<Genre> findAllByNameLike(String template);
}
