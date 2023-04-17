package com.example.soundcloudfinalprojectittalentss15.model.repositories;

import com.example.soundcloudfinalprojectittalentss15.model.entities.Playlist;
import com.example.soundcloudfinalprojectittalentss15.model.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);

    Optional<User> findById(int id);

    Optional<User> findByEmail(String email);

    void deleteById(int id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users SET display_name = :displayName, age = :age, gender = :gender, first_name = :firstName, last_name = :lastName, bio = :bio WHERE id = :userId", nativeQuery = true)
    void updateInfo(String displayName, int age, String gender, String firstName, String lastName, String bio, int userId);

    @Query(value = "SELECT u.* FROM followers AS f JOIN users AS u ON u.id = f.follower_id WHERE f.followed_id = :userId", nativeQuery = true)
    List<User> getFollowers(int userId);

    @Query(value = "SELECT u.* FROM followers AS f JOIN users AS u ON u.id = f.followed_id WHERE f.follower_id = :userId", nativeQuery = true)
    List<User> getFollowed(int userId);


}
