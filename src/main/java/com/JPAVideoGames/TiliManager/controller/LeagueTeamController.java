package com.JPAVideoGames.TiliManager.controller;

import com.JPAVideoGames.TiliManager.dto.TeamUpdateDTO;
import com.JPAVideoGames.TiliManager.exceptions.TeamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/equipo-ligas")
public class LeagueTeamController {

    @Autowired
    @Lazy

    @GetMapping
    public void getLeagueTeams() {

    }

    @PostMapping
    public void createLeagueTeam(@RequestBody TeamUpdateDTO localTeam) throws TeamException {

    }


}