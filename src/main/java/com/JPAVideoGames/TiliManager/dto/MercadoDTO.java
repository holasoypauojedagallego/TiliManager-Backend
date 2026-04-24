package com.JPAVideoGames.TiliManager.dto;

import com.JPAVideoGames.TiliManager.model.Player;

import java.util.List;

public class MercadoDTO {
    private List<Player> players;
    private boolean fichable;

    public MercadoDTO() {}

    public MercadoDTO(List<Player> players, boolean fichable) {
        this.players = players;
        this.fichable = fichable;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public boolean getFichable() {
        return fichable;
    }

    public void setFichable(boolean fichable) {
        this.fichable = fichable;
    }
}
