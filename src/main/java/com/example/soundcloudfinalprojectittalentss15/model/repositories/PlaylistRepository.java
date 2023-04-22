package com.example.soundcloudfinalprojectittalentss15.model.repositories;

import com.example.soundcloudfinalprojectittalentss15.model.entities.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {

    @Query("SELECT t FROM playlists t JOIN t.tags tag WHERE t.isPublic = true AND tag.name IN (:tags) GROUP BY t HAVING COUNT(t) = :size")
    Page<Playlist> findByTags(@Param("tags") List<String> tags, @Param("size") long size, Pageable pageable);

    @Query(value = "SELECT p.* FROM playlists AS p WHERE p.owner_id = :ownerId AND p.is_public = 1", nativeQuery = true)
    List<Playlist> findByOwnerIdAndIsPublic(int ownerId);


    @Query(value = "SELECT p.* FROM playlists AS p WHERE UPPER(p.title) LIKE UPPER(CONCAT('%', :title, '%')) AND p.is_public = 1 ", nativeQuery = true)
    List<Playlist> findAllByTitleContainingIgnoreCaseAndIsPublic(String title);


    @Query(value = "SELECT p.* FROM playlists p JOIN users_like_playlists ulp ON p.id = ulp.playlist_id WHERE ulp.user_id = :userId", nativeQuery = true)
    List<Playlist> findAllLikedPlaylistsByUserId(int userId);

}
