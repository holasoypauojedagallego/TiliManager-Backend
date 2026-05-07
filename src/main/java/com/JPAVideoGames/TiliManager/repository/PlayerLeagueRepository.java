package com.JPAVideoGames.TiliManager.repository;

import com.JPAVideoGames.TiliManager.model.PlayerLeague;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerLeagueRepository extends JpaRepository<PlayerLeague, Long> {
    List<PlayerLeague> findAllByTeamId(Long id);
    List<PlayerLeague> findAllByLeagueId(Long id);
    List<PlayerLeague> findByLeagueIdAndTeamId(Long leagueId, Long teamId);
}
