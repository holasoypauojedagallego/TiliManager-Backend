package com.JPAVideoGames.TiliManager.service;

import com.JPAVideoGames.TiliManager.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@Component
@EnableScheduling
public class MarketService {

    @Autowired
    private PlayerService playerService;

    private List<Player> players;
    private boolean fichable = false;

    @Scheduled(fixedRate = 6000) // (cron = "00 59 23 * * * ")
    public void scheduled() {
        mercadoJugadores();
        setFichable(false);
        System.out.println(isFichable());
    }

    @Scheduled(fixedRate = 4000) // (cron = "00 59 11 * * * ")
    public void scheduledFichable() {
        setFichable(true);
        System.out.println("a" +isFichable());
    }

    public void mercadoJugadores() {
        List<Player> jugadoresAnte = playerService.getJugadorByTeamIdNull();
        if (jugadoresAnte.size() <= 20) {
            setPlayers(jugadoresAnte);
            return;
        }
        Collections.shuffle(jugadoresAnte);
        setPlayers(jugadoresAnte.subList(0, 20));
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public boolean isFichable() {
        return fichable;
    }

    public void setFichable(boolean fichable) {
        this.fichable = fichable;
    }
}
