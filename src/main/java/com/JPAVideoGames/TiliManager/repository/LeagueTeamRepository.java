package com.JPAVideoGames.TiliManager.repository;

import com.JPAVideoGames.TiliManager.model.LeagueTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LeagueTeamRepository extends JpaRepository<LeagueTeam, Long> {
    List<LeagueTeam> findAllByTeamOwnerId(UUID id);
}
