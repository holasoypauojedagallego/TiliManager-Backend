package com.JPAVideoGames.TiliManager.controller;

import com.JPAVideoGames.TiliManager.dto.LeagueCreateDTO;
import com.JPAVideoGames.TiliManager.dto.LeagueDTO;
import com.JPAVideoGames.TiliManager.dto.TeamUpdateDTO;
import com.JPAVideoGames.TiliManager.exceptions.TeamException;
import com.JPAVideoGames.TiliManager.model.League;
import com.JPAVideoGames.TiliManager.service.LeagueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ligas")
public class LeagueController {

    @Autowired
    @Lazy
    private LeagueService leagueService;

    @GetMapping
    public ResponseEntity<List<LeagueDTO>> getLeagues() {
        return ResponseEntity.ok(leagueService.getAll());
    }

    @PostMapping
    public ResponseEntity<LeagueDTO> createLeague(@RequestBody @Valid LeagueCreateDTO leagueCreateDTO) {
        return ResponseEntity.ok(leagueService.createLeague(leagueCreateDTO));
    }


}