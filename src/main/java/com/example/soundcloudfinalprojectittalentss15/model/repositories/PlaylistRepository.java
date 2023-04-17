package com.example.soundcloudfinalprojectittalentss15.model.repositories;

import com.example.soundcloudfinalprojectittalentss15.model.entities.Playlist;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {

    List<Playlist> findByOwnerId(int ownerId);

    Optional<Playlist> getPlaylistsById(int id);

    List<Playlist> getAllByTitleContaining(String name);

    boolean existsByTitle(String title);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM playlists_have_tracks WHERE playlist_id = :playlistId AND track_id = :trackId", nativeQuery = true)
    void removeTrackFromPlaylist(int playlistId, int trackId);


}
