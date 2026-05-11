package com.JPAVideoGames.TiliManager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "player_league", uniqueConstraints = {
        @UniqueConstraint(name = "liga_unique_player_unique", columnNames = {"league_id", "player_id"})
})
public class PlayerLeague {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Player player;

    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league;

    @Column(name = "team_id")
    private Long teamId;

    @Column
    private int goles = 0;

    public PlayerLeague() {
    }

    public PlayerLeague(Player player) {
        this.player = player;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public int getGoles() {
        return goles;
    }

    public void setGoles(int goles) {
        this.goles = goles;
    }
}
