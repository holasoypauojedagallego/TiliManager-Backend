package com.JPAVideoGames.TiliManager.model;

import com.JPAVideoGames.TiliManager.dto.PartidoEncapsuladoDTO;
import jakarta.persistence.*;


import java.util.Date;
import java.util.List;

@Entity
@Table(name = "match")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ElementCollection
    @Column(nullable = false)
    private List<PartidoEncapsuladoDTO> partidoEncapsulado;

    @Column(nullable = false)
    private final Date date = new Date();

    @ManyToOne
    @JoinColumn(name = "local_team_id", referencedColumnName = "id")
    private Team localTeam;

    @ManyToOne
    @JoinColumn(name = "visitor_team_id", referencedColumnName = "id")
    private Team visitorTeam;

    @Column
    private int localTeamGoals = 0;

    @Column
    private int visitorTeamGoals = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "league_id")
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

    public Team getLocalTeam() {
        return localTeam;
    }

    public void setLocalTeam(Team localTeam) {
        this.localTeam = localTeam;
    }

    public Team getVisitorTeam() {
        return visitorTeam;
    }

    public void setVisitorTeam(Team visitorTeam) {
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
