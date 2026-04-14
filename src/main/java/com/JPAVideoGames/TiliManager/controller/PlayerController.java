package com.JPAVideoGames.TiliManager.controller;

import com.JPAVideoGames.TiliManager.model.PartidoEncapsulado;
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

    @GetMapping("/codigo")
    public ResponseEntity<List<PartidoEncapsulado>> codigoJugar() {
        return ResponseEntity.ok(playerService.codigo());
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
