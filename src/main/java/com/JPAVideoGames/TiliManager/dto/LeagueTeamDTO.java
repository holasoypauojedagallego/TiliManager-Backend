package com.JPAVideoGames.TiliManager.dto;

public class LeagueTeamDTO {

    private long id;

    private LeagueDTO league;

    private TeamDTO team;

    private int wins;
    private int losses;
    private int draws;
    private int goalsScored;
    private int goalsReceived;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LeagueDTO getLeague() {
        return league;
    }

    public void setLeague(LeagueDTO league) {
        this.league = league;
    }

    public TeamDTO getTeam() {
        return team;
    }

    public void setTeam(TeamDTO team) {
        this.team = team;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getPoints() {
        return ((this.wins * 3 ) + this.draws);
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    public int getGoalsReceived() {
        return goalsReceived;
    }

    public void setGoalsReceived(int goalsReceived) {
        this.goalsReceived = goalsReceived;
    }
}
