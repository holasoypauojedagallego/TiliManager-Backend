package com.JPAVideoGames.TiliManager.dto;

import java.util.Date;
import java.util.List;

public class MatchDTO {

    private long id;
    private List<PartidoEncapsuladoDTO> partidoEncapsulado;
    private final Date date = new Date();
    private TeamDTO localTeam;
    private TeamDTO visitorTeam;
    private int localTeamGoals = 0;
    private int visitorTeamGoals = 0;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<PartidoEncapsuladoDTO> getPartidoEncapsulado() {
        return partidoEncapsulado;
    }

    public void setPartidoEncapsulado(List<PartidoEncapsuladoDTO> partidoEncapsulado) {
        this.partidoEncapsulado = partidoEncapsulado;
    }

    public Date getDate() {
        return date;
    }

    public TeamDTO getLocalTeam() {
        return localTeam;
    }

    public void setLocalTeam(TeamDTO localTeam) {
        this.localTeam = localTeam;
    }

    public TeamDTO getVisitorTeam() {
        return visitorTeam;
    }

    public void setVisitorTeam(TeamDTO visitorTeam) {
        this.visitorTeam = visitorTeam;
    }

    public int getLocalTeamGoals() {
        return localTeamGoals;
    }

    public void setLocalTeamGoals(int localTeamGoals) {
        this.localTeamGoals = localTeamGoals;
    }

    public int getVisitorTeamGoals() {
        return visitorTeamGoals;
    }

    public void setVisitorTeamGoals(int visitorTeamGoals) {
        this.visitorTeamGoals = visitorTeamGoals;
    }
}
