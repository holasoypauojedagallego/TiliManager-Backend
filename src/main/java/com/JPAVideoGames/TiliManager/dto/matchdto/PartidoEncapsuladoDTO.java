package com.JPAVideoGames.TiliManager.dto.matchdto;

import com.JPAVideoGames.TiliManager.model.Player;
import com.JPAVideoGames.TiliManager.model.Team;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class PartidoEncapsuladoDTO {

    @Column
    private int minuto;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team equipo;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player jugador;

    @Column
    private boolean local;
    @Column
    private int sucede;
    @Column
    private int golesLocal;
    @Column
    private int golesVisitante;

    public PartidoEncapsuladoDTO(int minuto) {
        this.minuto = minuto;
    }

    public PartidoEncapsuladoDTO() {}

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