package com.example.netcracker_lab_2.repositiry;

import com.example.netcracker_lab_2.domain.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository  extends CrudRepository<Genre, Integer> {

}
