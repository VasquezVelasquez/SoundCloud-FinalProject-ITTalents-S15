package com.example.soundcloudfinalprojectittalentss15.model.repositories;

import com.example.soundcloudfinalprojectittalentss15.model.entities.Playlist;
import com.example.soundcloudfinalprojectittalentss15.model.entities.Track;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {


    @Query("SELECT t FROM playlists t JOIN t.tags tag WHERE tag.name IN (:tags) GROUP BY t HAVING COUNT(t) = :size")
    Page<Playlist> findByTags(@Param("tags") List<String> tags, @Param("size") long size, Pageable pageable);

    List<Playlist> findByOwnerId(int ownerId);

    Optional<Playlist> getPlaylistsById(int id);

    List<Playlist> getAllByTitleContainingIgnoreCase(String name);

    boolean existsByTitle(String title);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM playlists_have_tracks WHERE playlist_id = :playlistId AND track_id = :trackId", nativeQuery = true)
    void removeTrackFromPlaylist(int playlistId, int trackId);

    @Query(value = "SELECT p.* FROM playlists p JOIN users_like_playlists ulp ON p.id = ulp.playlist_id WHERE ulp.user_id = :userId", nativeQuery = true)
    List<Playlist> findAllLikedPlaylistsByUserId(int userId);

}
