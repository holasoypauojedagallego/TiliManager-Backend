package com.JPAVideoGames.TiliManager.repository;

import com.JPAVideoGames.TiliManager.model.League;
import com.JPAVideoGames.TiliManager.model.UserTili;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {
    long countByOwner(UserTili owner);
    Optional<League> findByOwnerAndId(UserTili userTili, long id);
    Optional<League> findById(long id);
}
