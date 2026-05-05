package com.JPAVideoGames.TiliManager.dto.leaguedto;

import com.JPAVideoGames.TiliManager.dto.usertilidto.UserTiliPassDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LeagueCreateDTO {

    @NotBlank()
    @Size(min = 3, max = 33)
    @Pattern(regexp = "^[a-zA-Z0-9._+-]([a-zA-Z0-9._+ -]*[a-zA-Z0-9._+-])?$")
    private String name;

    @Valid
    @NotNull
    private UserTiliPassDTO owner;

    private boolean closed = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserTiliPassDTO getOwner() {
        return owner;
    }

    public void setOwner(UserTiliPassDTO owner) {
        this.owner = owner;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
