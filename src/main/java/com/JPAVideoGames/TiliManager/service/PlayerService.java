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

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    // La diferencia entre findAll y getJugadores es que, hay ciertos jugadores especiales (específicos para los bots) que no quiero meter en una liga
    public List<Player> getJugadores() {
        return playerRepository.findAllBySpecial(false);
    }

    public List<Player> getJugadoresEspecificos() {
        return playerRepository.findAllBySpecial(true);
    }

    public Optional<Player> getJugador(long id) {
        return playerRepository.findById(id);
    }

}