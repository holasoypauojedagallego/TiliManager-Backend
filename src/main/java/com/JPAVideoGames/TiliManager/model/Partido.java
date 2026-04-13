package com.JPAVideoGames.TiliManager.model;

public class Partido {
    private int minuto;
    private String sucede;

    public Partido(int minuto) {
        this.minuto = minuto;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    public String getSucede() {
        return sucede;
    }

    public void setSucede(String sucede) {
        this.sucede = sucede;
    }
}
