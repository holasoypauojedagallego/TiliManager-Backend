package com.JPAVideoGames.TiliManager.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "league")
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserTili owner;

    @OneToMany(mappedBy = "league", cascade = CascadeType.ALL)
    private List<LeagueTeam> teams;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "league")
    private List<PlayerLeague> players;

    @Column
    private boolean closed = false; // Liga privada = true, liga pública = false

    public League() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserTili getOwner() {
        return owner;
    }

    public void setOwner(UserTili owner) {
        this.owner = owner;
    }

    public List<LeagueTeam> getTeams() {
        return teams;
    }

    public void setTeams(List<LeagueTeam> teams) {
        if (teams.size() > 20) throw new IllegalArgumentException("Max of 20 teams allowed");
        this.teams = teams;
    }

    public void setOneTeam(LeagueTeam team){
        if (this.teams.size() >= 20) throw new IllegalArgumentException("Max of 20 teams allowed");
        this.teams.add(team);
    }

    public List<PlayerLeague> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerLeague> players) {
        this.players = players;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
