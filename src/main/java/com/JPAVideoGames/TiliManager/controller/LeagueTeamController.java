package com.JPAVideoGames.TiliManager.controller;

import com.JPAVideoGames.TiliManager.dto.leagueteamdto.LeagueTeamDTO;
import com.JPAVideoGames.TiliManager.dto.teamdto.TeamUpdateDTO;
import com.JPAVideoGames.TiliManager.dto.usertilidto.UserTiliPassDTO;
import com.JPAVideoGames.TiliManager.exceptions.TeamException;
import com.JPAVideoGames.TiliManager.service.LeagueTeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipo-ligas")
public class LeagueTeamController {

    @Autowired
    @Lazy
    private LeagueTeamService leagueTeamService;

    @GetMapping
    public ResponseEntity<List<LeagueTeamDTO>> getLeagueTeams() {
        return ResponseEntity.ok(leagueTeamService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeagueTeamDTO> getTeamsByOwner(@PathVariable Long id) {
        return leagueTeamService.getByIdDTO(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<List<LeagueTeamDTO>> getTeamsByOwner(@RequestBody @Valid UserTiliPassDTO userTiliPassDTO) {
        return ResponseEntity.ok(leagueTeamService.getAllByTeamOwnerId(userTiliPassDTO));
    }



}