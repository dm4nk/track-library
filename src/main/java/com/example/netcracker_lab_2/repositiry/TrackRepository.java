package com.example.netcracker_lab_2.repositiry;

import com.example.netcracker_lab_2.domain.Track;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends CrudRepository<Track, Integer> {

}
