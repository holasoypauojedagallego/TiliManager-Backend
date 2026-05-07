package com.JPAVideoGames.TiliManager.dto.teamdto;

import com.JPAVideoGames.TiliManager.dto.playerleague.PlayerLeagueDTO;
import com.JPAVideoGames.TiliManager.model.Player;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class VenderDTO {

    @Valid
    @NotNull
    private TeamUpdateDTO teamUpdateDTO;
    private PlayerLeagueDTO player;

    public VenderDTO() {}

    public TeamUpdateDTO getTeamUpdateDTO() {
        return teamUpdateDTO;
    }

    public void setTeamUpdateDTO(TeamUpdateDTO teamUpdateDTO) {
        this.teamUpdateDTO = teamUpdateDTO;
    }

    public PlayerLeagueDTO getPlayer() {
        return player;
    }

    public void setPlayer(PlayerLeagueDTO player) {
        this.player = player;
    }
}
