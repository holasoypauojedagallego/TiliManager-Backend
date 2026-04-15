package com.JPAVideoGames.TiliManager.controller;

import com.JPAVideoGames.TiliManager.dto.PartidoEncapsuladoDTO;
import com.JPAVideoGames.TiliManager.model.Player;
import com.JPAVideoGames.TiliManager.model.Team;
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

    @GetMapping("/equipos")
    public ResponseEntity<List<Team>> getEquipos() {
        return ResponseEntity.ok(playerService.getEquipo());
    }

    @GetMapping("/codigo")
    public ResponseEntity<List<PartidoEncapsuladoDTO>> codigoJugarSimulado() {
        Team localTeamRating = playerService.getEquipo().get(0);
        Team visitorTeamRating = playerService.getEquipo().get(1);
        return ResponseEntity.ok(playerService.codigo(localTeamRating, visitorTeamRating));
    }

    @PostMapping("/codigo")
    public ResponseEntity<List<PartidoEncapsuladoDTO>> codigoJugar(@RequestBody Team localTeam, Team visitorTeam) {
        return ResponseEntity.ok(playerService.codigo(localTeam, visitorTeam));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getJugador(@PathVariable long id) {
        Team attackerTeam = playerService.getEquipo().get(0);
        Team defenderTeam = playerService.getEquipo().get(1);
        playerService.gol(
                attackerTeam.getPlayers().stream().mapToInt(Player::getAttack).sum(),
                defenderTeam.getPlayers().stream().mapToInt(Player::getDefense).sum()
        );

        return playerService.getJugador(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Player> postJugador(@RequestBody Player player) {
        return ResponseEntity.ok(playerService.postJugador(player)); // Hay que cambiarlo a 'created' no 'ok'
    }

}