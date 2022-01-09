package com.example.netcracker_lab_2.repositiry;

import com.example.netcracker_lab_2.domain.Genre;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Integer> {
    @Query("select g from Genre g where g.name = ?1")
    Optional<Genre> findByName(String name);
}
