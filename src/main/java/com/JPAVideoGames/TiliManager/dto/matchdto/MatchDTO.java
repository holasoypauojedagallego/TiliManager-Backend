package com.JPAVideoGames.TiliManager.dto.matchdto;

import com.JPAVideoGames.TiliManager.dto.teamdto.TeamDTO;
import com.JPAVideoGames.TiliManager.model.League;

import java.util.Date;
import java.util.List;

public class MatchDTO {

    private long id;
    private List<PartidoEncapsuladoDTO> partidoEncapsulado;
    private Date date;
    private TeamDTO localTeam;
    private TeamDTO visitorTeam;
    private int localTeamGoals = 0;
    private int visitorTeamGoals = 0;
    private League league;

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

    public void setDate(Date date) {
        this.date = date;
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

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }
}
