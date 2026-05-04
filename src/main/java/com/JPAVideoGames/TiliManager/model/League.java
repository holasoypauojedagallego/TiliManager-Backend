package com.JPAVideoGames.TiliManager.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "league")
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserTili owner;

    @OneToMany(mappedBy = "league", cascade = CascadeType.ALL)
    private List<LeagueTeam> equipos;
}
