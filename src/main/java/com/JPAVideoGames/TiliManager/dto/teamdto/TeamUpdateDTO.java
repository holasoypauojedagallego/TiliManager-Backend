package com.JPAVideoGames.TiliManager.dto.teamdto;

import com.JPAVideoGames.TiliManager.dto.usertilidto.UserTiliPassDTO;
import com.JPAVideoGames.TiliManager.model.Player;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class TeamUpdateDTO {
    private long id;
    private String name;
    private List<Player> players;

    @Valid
    @NotNull
    private UserTiliPassDTO owner;

    private Long money;

    public TeamUpdateDTO() {}

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

    public UserTiliPassDTO getOwner() {
        return owner;
    }

    public void setOwner(UserTiliPassDTO owner) {
        this.owner = owner;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }
}
