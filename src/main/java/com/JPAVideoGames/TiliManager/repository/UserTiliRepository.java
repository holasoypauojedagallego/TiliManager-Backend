package com.JPAVideoGames.TiliManager.repository;

import com.JPAVideoGames.TiliManager.model.UserTili;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserTiliRepository extends JpaRepository<UserTili, Long> {
    Optional<UserTili> findByEmail(String email);
    @Query("SELECT u FROM UserTili u WHERE u.email LIKE 'liga%'")
    List<UserTili> findAllByEmail();
    Optional<UserTili> findByName(String email);
    Optional<UserTili> findById(UUID uuid);
}
