package com.JPAVideoGames.TiliManager.model;

public class PartidoEncapsulado {
    private int minuto;
    private String equipo;
    private int sucede;

    public PartidoEncapsulado(int minuto) {
        this.minuto = minuto;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public int getSucede() {
        return sucede;
    }

    public void setSucede(int sucede) {
        this.sucede = sucede;
    }
}
