package com.JPAVideoGames.TiliManager.dto;

import com.JPAVideoGames.TiliManager.model.Player;

import java.util.List;

public class TeamUpdateDTO {

    private long id;
    private String name;
    private List<Player> players;
    private long price;

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

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
