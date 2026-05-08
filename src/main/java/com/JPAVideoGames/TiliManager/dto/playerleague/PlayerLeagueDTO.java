package com.JPAVideoGames.TiliManager.dto.playerleague;

import com.JPAVideoGames.TiliManager.dto.leaguedto.LeagueIdDTO;
import com.JPAVideoGames.TiliManager.model.Player;

public class PlayerLeagueDTO {
    private long id;
    private Player player;
    private LeagueIdDTO league;
    private Long teamId;

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

    public LeagueIdDTO getLeague() {
        return league;
    }

    public void setLeague(LeagueIdDTO league) {
        this.league = league;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
}
