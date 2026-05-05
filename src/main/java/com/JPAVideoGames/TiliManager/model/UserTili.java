package com.JPAVideoGames.TiliManager.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "user_tili")
public class UserTili {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Team team;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserTiliRole role =  UserTiliRole.USUARIO;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserTiliRole getRole() {
        return role;
    }

    public void setRole(UserTiliRole role) {
        this.role = role;
    }
}
