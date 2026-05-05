package com.JPAVideoGames.TiliManager.dto.teamdto;

import com.JPAVideoGames.TiliManager.model.Player;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class VenderDTO {

    @Valid
    @NotNull
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
