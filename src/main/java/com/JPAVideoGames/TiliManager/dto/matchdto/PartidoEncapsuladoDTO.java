package com.JPAVideoGames.TiliManager.dto.matchdto;

import com.JPAVideoGames.TiliManager.dto.playerleague.PlayerLeagueDTO;
import com.JPAVideoGames.TiliManager.dto.teamdto.TeamDTO;
import com.JPAVideoGames.TiliManager.model.Player;
import com.JPAVideoGames.TiliManager.model.Team;

public class PartidoEncapsuladoDTO {

    private int minuto;
    private TeamDTO equipo;
    private PlayerLeagueDTO jugador;
    private boolean local;
    private int sucede;
    private int golesLocal;
    private int golesVisitante;

    public PartidoEncapsuladoDTO() {}

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    public TeamDTO getEquipo() {
        return equipo;
    }

    public void setEquipo(TeamDTO equipo) {
        this.equipo = equipo;
    }

    public PlayerLeagueDTO getJugador() {
        return jugador;
    }

    public void setJugador(PlayerLeagueDTO jugador) {
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

    public int getGolesLocal() {
        return golesLocal;
    }

    public void setGolesLocal(int golesLocal) {
        this.golesLocal = golesLocal;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }

    public void setGolesVisitante(int golesVisitante) {
        this.golesVisitante = golesVisitante;
    }
}