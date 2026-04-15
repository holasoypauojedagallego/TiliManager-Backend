package com.JPAVideoGames.TiliManager.dto;

import com.JPAVideoGames.TiliManager.model.Player;
import com.JPAVideoGames.TiliManager.model.Team;

public class PartidoEncapsuladoDTO {
    private int minuto;
    private Team equipo;
    private Player jugador;
    private boolean local;
    private int sucede;

    public PartidoEncapsuladoDTO(int minuto) {
        this.minuto = minuto;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    public Team getEquipo() {
        return equipo;
    }

    public void setEquipo(Team equipo) {
        this.equipo = equipo;
    }

    public Player getJugador() {
        return jugador;
    }

    public void setJugador(Player jugador) {
        this.jugador = jugador;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public int getSucede() {
        return sucede;
    }

    public void setSucede(int sucede) {
        this.sucede = sucede;
    }
}