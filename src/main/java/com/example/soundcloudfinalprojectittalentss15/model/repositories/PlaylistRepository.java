package com.example.soundcloudfinalprojectittalentss15.model.repositories;

import com.example.soundcloudfinalprojectittalentss15.model.entities.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {

    List<Playlist> findByOwnerId(int ownerId);
}
