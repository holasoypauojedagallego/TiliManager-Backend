package com.JPAVideoGames.TiliManager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class PartidoEncapsulado {

    @Column
    private int minuto;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team equipo;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private PlayerLeague jugador;

    @Column
    private boolean local;
    @Column
    private int sucede;
    @Column
    private int golesLocal;
    @Column
    private int golesVisitante;

    public PartidoEncapsulado(int minuto) {
        this.minuto = minuto;
    }

    public PartidoEncapsulado() {}

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

    public PlayerLeague getJugador() {
        return jugador;
    }

    public void setJugador(PlayerLeague jugador) {
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