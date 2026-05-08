package com.JPAVideoGames.TiliManager.controller;

import com.JPAVideoGames.TiliManager.dto.playerleague.PlayerLeagueDTO;
import com.JPAVideoGames.TiliManager.model.Player;
import com.JPAVideoGames.TiliManager.service.PlayerLeagueService;
import com.JPAVideoGames.TiliManager.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/jugadores_liga")
public class PlayerLeagueController {

    @Autowired
    @Lazy
    private PlayerLeagueService playerLeagueService;

    @GetMapping
    public ResponseEntity<List<PlayerLeagueDTO>> getJugadores() {
        return ResponseEntity.ok(playerLeagueService.getJugadores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerLeagueDTO> getJugador(@PathVariable long id) {
        return playerLeagueService.getJugador(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/liga/{id}")
    public ResponseEntity<List<PlayerLeagueDTO>> getJugadorByLeague(@PathVariable long id) {
        return ResponseEntity.ok(playerLeagueService.getJugadoresByLeague(id));
    }

    @GetMapping("/liga_vacios/{id}")
    public ResponseEntity<List<PlayerLeagueDTO>> getJugadorByLeagueAndTeamNull(@PathVariable long id) {
        return ResponseEntity.ok(playerLeagueService.getJugadoresByLeagueAndTeamIdNull(id));
    }
}