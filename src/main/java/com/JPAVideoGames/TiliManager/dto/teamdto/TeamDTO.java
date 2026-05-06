package com.JPAVideoGames.TiliManager.dto.teamdto;

import com.JPAVideoGames.TiliManager.dto.leagueteamdto.LeagueTeamIdDTO;
import com.JPAVideoGames.TiliManager.dto.usertilidto.UserTiliDTO;
import com.JPAVideoGames.TiliManager.model.Player;
import java.util.List;

public class TeamDTO {

    private long id;
    private String name;
    private List<Player> players;
    private UserTiliDTO owner;
    private Long money;
    private LeagueTeamIdDTO leagueTeam;

    public TeamDTO() {}

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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public UserTiliDTO getOwner() {
        return owner;
    }

    public void setOwner(UserTiliDTO owner) {
        this.owner = owner;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public LeagueTeamIdDTO getLeagueTeam() {
        return leagueTeam;
    }

    public void setLeagueTeam(LeagueTeamIdDTO leagueTeam) {
        this.leagueTeam = leagueTeam;
    }
}
