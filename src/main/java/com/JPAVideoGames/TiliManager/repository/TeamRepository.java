package com.JPAVideoGames.TiliManager.repository;

import com.JPAVideoGames.TiliManager.model.Team;
import com.JPAVideoGames.TiliManager.model.UserTili;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findById(long id);
    Optional<Team> findByName(String name);
    Optional<Team> findByOwner(UserTili owner);
}
