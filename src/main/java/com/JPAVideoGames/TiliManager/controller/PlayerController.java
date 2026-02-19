package com.JPAVideoGames.TiliManager.controller;

import com.JPAVideoGames.TiliManager.model.Player;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/jugadores")
public class PlayerController {
    private static List<Player> jugadores = new ArrayList<>(List.of(
            new Player(1, "Pau ", "Barça"),
            new Player(2, "Adrian ", "Atleti"),
            new Player(3, "Justin ", "Madrid"),
            new Player(4, "Tadi", "Real Sociedadw")));


    @GetMapping
    public List<Player> listaJugadores() {
        return jugadores;
    }

    @GetMapping("/{id}")
    public Player getJugador(@PathVariable long id) {
        for (Player p : jugadores) {
            if (p.getId() == id){
                return p;
            }
        }
        return null;
    }

    @PostMapping
    public Player postJugador(@RequestBody Player player) {
        jugadores.add(player);
        return player;
    }
}
