package com.JPAVideoGames.TiliManager.dto;

import com.JPAVideoGames.TiliManager.model.Player;

public class VenderDTO {
    private TeamUpdateDTO teamUpdateDTO;
    private Player player;

    public VenderDTO() {}

    public TeamUpdateDTO getTeamUpdateDTO() {
        return teamUpdateDTO;
    }

    public void setTeamUpdateDTO(TeamUpdateDTO teamUpdateDTO) {
        this.teamUpdateDTO = teamUpdateDTO;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
