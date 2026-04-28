package com.JPAVideoGames.TiliManager.service;

import com.JPAVideoGames.TiliManager.dto.MercadoDTO;
import com.JPAVideoGames.TiliManager.model.Player;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@EnableScheduling
public class MarketService {

    @Autowired
    private PlayerService playerService;

    private MercadoDTO mercadoDTO;

    private List<Player> players;

    @PostConstruct
    public void init() {
        mercadoJugadores();
        this.mercadoDTO = new MercadoDTO(getPlayers(), true);
    }

    @Scheduled(cron = "00 59 23 * * * ")
    public void scheduled() {
        mercadoJugadores();
        this.mercadoDTO = new MercadoDTO(getPlayers(), false);
    }

    @Scheduled(cron = "00 21 16 * * * ")
    public void scheduledFichable() {
        if (this.mercadoDTO != null && !this.mercadoDTO.getPlayers().isEmpty()) {
            this.mercadoDTO.setFichable(true);
        }
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

    public void actualizarMercado(Player p){
        for (int i = 0; i < getPlayers().size(); i++) {
            if (p == getPlayers().get(i)){
                this.players.set(i, p);
            }
        }
    }

    public MercadoDTO getMercadoDTO() {
        return mercadoDTO;
    }

    private void setMercadoDTO(MercadoDTO mercadoDTO) {
        this.mercadoDTO = mercadoDTO;
    }

    private List<Player> getPlayers() {
        return players;
    }

    private void setPlayers(List<Player> players) {
        this.players = players;
    }

}
