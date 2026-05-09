package com.JPAVideoGames.TiliManager.dto.marketdto;

import com.JPAVideoGames.TiliManager.dto.playerleague.PlayerLeagueDTO;

import java.util.List;

public class MercadoDTO {
    private long id;
    private List<PlayerLeagueDTO> players;
    private boolean fichable;

    public MercadoDTO() {}

    public MercadoDTO(long id,List<PlayerLeagueDTO> players, boolean fichable) {
        this.id = id;
        this.players = players;
        this.fichable = fichable;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<PlayerLeagueDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerLeagueDTO> players) {
        this.players = players;
    }

    public boolean getFichable() {
        return fichable;
    }

    public void setFichable(boolean fichable) {
        this.fichable = fichable;
    }
}
