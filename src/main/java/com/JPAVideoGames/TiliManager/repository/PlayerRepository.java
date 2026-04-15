package com.JPAVideoGames.TiliManager.repository;

import com.JPAVideoGames.TiliManager.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
}
