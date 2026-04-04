package com.JPAVideoGames.TiliManager.repository;

import com.JPAVideoGames.TiliManager.model.UserTili;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTiliRepository extends JpaRepository<UserTili, Long> {
    Optional<UserTili> findByEmail(String email);
}
