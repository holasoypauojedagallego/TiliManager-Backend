package com.JPAVideoGames.TiliManager.model;

import com.JPAVideoGames.TiliManager.dto.leaguedto.LeagueIdDTO;
import com.JPAVideoGames.TiliManager.dto.usertilidto.UserTiliPassDTO;
import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "admin_logs")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String log;

    @Column
    private UUID userTiliId;

    @Column
    private Long leagueId;

    @Column
    private final Date date = new Date();

    public Admin() {}

    public Admin(String log, UUID userTiliId) {
        this.log = log;
        this.userTiliId = userTiliId;
    }

    public Admin(String log, Long leagueId) {
        this.log = log;
        this.leagueId = leagueId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public UUID getUserTili() {
        return userTiliId;
    }

    public void setUserTili(UUID userTili) {
        this.userTiliId = userTili;
    }

    public Long getLeague() {
        return leagueId;
    }

    public void setLeague(Long league) {
        this.leagueId = league;
    }

    public Date getDate() {
        return date;
    }
}
