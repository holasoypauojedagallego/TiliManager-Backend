package com.JPAVideoGames.TiliManager.model;

import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private long id;
    private String owner;
    private String name;

    @Size(min = 5, max = 7)
    private List<Player> players;

    public Team(long id, String owner, String name, List<Player> players) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        if (players.size() > 7) throw new IllegalArgumentException("Max of 7 players allowed");
        this.players = new ArrayList<>(players);
    }

    public Team() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) throws IllegalArgumentException {
        if (players.size() > 7) throw new IllegalArgumentException("Max of 7 players allowed");
        this.players = players;
    }

    public void setOnePlayer(Player player) throws IllegalArgumentException {
        if (this.players.size() >= 7) throw new IllegalArgumentException("Max of 7 players allowed");
        this.players.add(player);
    }
}
