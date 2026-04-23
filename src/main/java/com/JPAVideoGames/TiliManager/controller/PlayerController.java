package com.JPAVideoGames.TiliManager.controller;

import com.JPAVideoGames.TiliManager.model.Player;
import com.JPAVideoGames.TiliManager.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/jugadores")
public class PlayerController {

    @Autowired
    @Lazy
    private PlayerService playerService;

    @GetMapping
    public ResponseEntity<List<Player>> getJugadores() {
        return ResponseEntity.ok(playerService.getJugadores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getJugador(@PathVariable long id) {
        return playerService.getJugador(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/teamid")
    public ResponseEntity<List<Player>> getJugadoresByTeamIDNull() {
        return ResponseEntity.ok(playerService.getJugadorByTeamIdNull());
    }

    @GetMapping("/teamid/{id}")
    public ResponseEntity<List<Player>> getJugadoresByTeamID(@PathVariable long id) {
        return ResponseEntity.ok(playerService.getJugadorByTeamId(id));
    }

}