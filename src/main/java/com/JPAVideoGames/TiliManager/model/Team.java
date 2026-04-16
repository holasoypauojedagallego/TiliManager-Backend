package com.JPAVideoGames.TiliManager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    @Size(min = 3, max = 16)
    @Pattern(regexp = "^[a-zA-Z0-9._+-]+$", message = "Ha de tener caracteres válidos (a-zA-Z0-9._+-)")
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private List<Player> players;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner", referencedColumnName = "id", unique = true)
    private UserTili owner;

    public Team(long id, UserTili owner, String name, List<Player> players) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        if (players.size() > 7) throw new IllegalArgumentException("Max of 7 players allowed");
        this.players = new ArrayList<>(players);
    }

    public Team() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserTili getOwner() {
        return owner;
    }

    public void setOwner(UserTili owner) {
        this.owner = owner;
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

    public void setPlayers(List<Player> players) throws IllegalArgumentException {
        if (players.size() > 7) throw new IllegalArgumentException("Max of 7 players allowed");
        this.players = players;
    }

    public void setOnePlayer(Player player) throws IllegalArgumentException {
        if (this.players.size() >= 7) throw new IllegalArgumentException("Max of 7 players allowed");
        this.players.add(player);
    }
}
