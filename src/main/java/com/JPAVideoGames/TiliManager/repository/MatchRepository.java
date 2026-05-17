package com.JPAVideoGames.TiliManager.repository;

import com.JPAVideoGames.TiliManager.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    Optional<Match> findById(long id);
    List<Match> findAllByLeagueId(Long id);
}
