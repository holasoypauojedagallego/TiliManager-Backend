package com.JPAVideoGames.TiliManager.model;

public class Player {

    private final long id;
    private String name;
    private String team;

    public Player(long id, String name, String team) {
        this.id = id;
        this.name = name;
        this.team = team;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}
