package com.JPAVideoGames.TiliManager.repository;

import com.JPAVideoGames.TiliManager.model.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {
}
