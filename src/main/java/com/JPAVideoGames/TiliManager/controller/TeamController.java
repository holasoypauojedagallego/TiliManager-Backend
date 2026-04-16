package com.JPAVideoGames.TiliManager.controller;

import com.JPAVideoGames.TiliManager.dto.TeamDTO;
import com.JPAVideoGames.TiliManager.model.Team;
import com.JPAVideoGames.TiliManager.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipos")
public class TeamController {

    @Autowired
    @Lazy
    private TeamService teamService;

    @GetMapping
    public ResponseEntity<List<TeamDTO>> getTeams() {
        return ResponseEntity.ok(teamService.getTeams());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable long id) {
        return teamService.getTeamById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable String name) {
        return teamService.getTeamByName(name).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

}
