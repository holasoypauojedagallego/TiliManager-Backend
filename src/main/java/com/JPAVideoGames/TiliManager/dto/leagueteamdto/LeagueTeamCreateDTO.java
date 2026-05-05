package com.JPAVideoGames.TiliManager.dto.leagueteamdto;

import com.JPAVideoGames.TiliManager.dto.teamdto.TeamUpdateDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class LeagueTeamCreateDTO {
    private long idliga;

    @Valid
    @NotNull
    private TeamUpdateDTO teamUpdateDTO;

    public long getIdliga() {
        return idliga;
    }

    public void setIdliga(long idliga) {
        this.idliga = idliga;
    }

    public TeamUpdateDTO getTeamUpdateDTO() {
        return teamUpdateDTO;
    }

    public void setTeamUpdateDTO(TeamUpdateDTO teamUpdateDTO) {
        this.teamUpdateDTO = teamUpdateDTO;
    }
}
