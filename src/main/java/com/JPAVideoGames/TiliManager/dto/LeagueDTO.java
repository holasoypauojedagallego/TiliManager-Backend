package com.JPAVideoGames.TiliManager.dto;

import java.util.List;

public class LeagueDTO {
    private long id;
    private String name;
    private UserTiliDTO owner;
    private List<LeagueTeamDTO> teams;
    private boolean closed;

    public LeagueDTO() {}

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

    public UserTiliDTO getOwner() {
        return owner;
    }

    public void setOwner(UserTiliDTO owner) {
        this.owner = owner;
    }

    public List<LeagueTeamDTO> getTeams() {
        return teams;
    }

    public void setTeams(List<LeagueTeamDTO> teams) {
        this.teams = teams;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
