package com.example.soundcloudfinalprojectittalentss15.model.repositories;

import com.example.soundcloudfinalprojectittalentss15.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);

    Optional<User> findById(int id);

    Optional<User> findByEmail(String email);
}
