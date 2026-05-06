package com.JPAVideoGames.TiliManager.repository;

import com.JPAVideoGames.TiliManager.model.LeagueTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueTeamRepository extends JpaRepository<LeagueTeam, Long> {
}
