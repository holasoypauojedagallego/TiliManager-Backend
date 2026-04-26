package com.JPAVideoGames.TiliManager.controller;

import com.JPAVideoGames.TiliManager.dto.TeamUpdateDTO;
import com.JPAVideoGames.TiliManager.dto.TeamDTO;
import com.JPAVideoGames.TiliManager.dto.UserTiliPassDTO;
import com.JPAVideoGames.TiliManager.exceptions.PlayersSizeException;
import com.JPAVideoGames.TiliManager.model.Player;
import com.JPAVideoGames.TiliManager.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    public ResponseEntity<TeamDTO> getTeamByName(@PathVariable String name) {
        return teamService.getTeamByName(name).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/exists/name/{name}")
    public ResponseEntity<Boolean> existsTeamByName(@PathVariable String name) {
        return ResponseEntity.ok(teamService.getTeamByName(name).isPresent());
    }

    @PostMapping("/owner")
    public ResponseEntity<TeamDTO> getTeamByOwner(@RequestBody @Valid UserTiliPassDTO userTiliPassDTO) {
        return teamService.getTeamByOwner(userTiliPassDTO).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<TeamDTO> updateCreateTeam(@RequestBody TeamUpdateDTO teamUpdateDTO) throws PlayersSizeException {
        return teamService.updateCreateTeam(teamUpdateDTO).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/vender")
    public ResponseEntity<TeamDTO> sellPlayer(@RequestBody TeamUpdateDTO teamUpdateDTO, Player player) {
        return teamService.venderJugador(teamUpdateDTO, player).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

}
