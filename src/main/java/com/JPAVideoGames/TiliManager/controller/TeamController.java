package com.JPAVideoGames.TiliManager.controller;

import com.JPAVideoGames.TiliManager.model.Team;
import com.JPAVideoGames.TiliManager.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/equipos")
public class TeamController {

    @Autowired
    @Lazy
    private TeamService teamService;

    @GetMapping
    public ResponseEntity<List<Team>> getTeams() {
        return ResponseEntity.ok(teamService.getTeams());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable long id) {
        return teamService.getTeamById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Team> getTeamById(@PathVariable String name) {
        return teamService.getTeamByName(name).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
