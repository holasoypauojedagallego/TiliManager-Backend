package com.JPAVideoGames.TiliManager.service;

import com.JPAVideoGames.TiliManager.model.Player;
import com.JPAVideoGames.TiliManager.repository.PlayerRepository;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Lazy
@Transactional
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getJugadores() {
        return playerRepository.findAll();
    }

    public Optional<Player> getJugador(long id) {
        return playerRepository.findById(id);
    }

    public List<Player> getJugadorByTeamId(Long id) {
        return playerRepository.findByTeamId(id);
    }

    public List<Player> getJugadorByTeamIdNull() {
        return playerRepository.findByTeamId(null);
    }

}