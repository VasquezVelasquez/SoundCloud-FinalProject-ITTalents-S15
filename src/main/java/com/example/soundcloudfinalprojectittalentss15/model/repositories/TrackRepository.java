package com.example.soundcloudfinalprojectittalentss15.model.repositories;

import com.example.soundcloudfinalprojectittalentss15.model.entities.Track;
import com.example.soundcloudfinalprojectittalentss15.model.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrackRepository extends JpaRepository<Track, Integer> {

    Optional<Track> getById(int id);

    Track findByTrackUrl(String url);

    List<Track> findAllByOwner(User owner);

    List<Track> findAllByTitleContainingIgnoreCase(String title);

    Page<Track> findByIsPublicTrue(Pageable pageable);
}
