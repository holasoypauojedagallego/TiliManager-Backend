package com.JPAVideoGames.TiliManager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    @Size(min = 3, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9._+-]([a-zA-Z0-9._+ -]*[a-zA-Z0-9._+-])?$")
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private List<PlayerLeague> players;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserTili owner;

    @Column(nullable = false)
    private Long money;

    @OneToOne(mappedBy = "team")
    private LeagueTeam leagueTeam;

    public Team() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserTili getOwner() {
        return owner;
    }

    public void setOwner(UserTili owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PlayerLeague> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerLeague> players) throws IllegalArgumentException {
        if (players.size() > 7) throw new IllegalArgumentException("Max of 7 players allowed");
        this.players = players;
    }

    public void setOnePlayer(PlayerLeague player) throws IllegalArgumentException {
        if (this.players.size() >= 7) throw new IllegalArgumentException("Max of 7 players allowed");
        this.players.add(player);
    }

    public void deleteOnePlayer(PlayerLeague player) throws IllegalArgumentException {
        if (this.players.isEmpty()) throw new IllegalArgumentException("Min of 0 players allowed");
        this.players.removeIf(p -> p.getPlayer().getId() == player.getId());
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public LeagueTeam getLeagueTeam() {
        return leagueTeam;
    }

    public void setLeagueTeam(LeagueTeam leagueTeam) {
        this.leagueTeam = leagueTeam;
    }
}
