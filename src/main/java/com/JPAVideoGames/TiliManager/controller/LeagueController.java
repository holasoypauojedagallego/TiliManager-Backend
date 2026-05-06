package com.JPAVideoGames.TiliManager.controller;

import com.JPAVideoGames.TiliManager.dto.leaguedto.LeagueCreateDTO;
import com.JPAVideoGames.TiliManager.dto.leaguedto.LeagueDTO;
import com.JPAVideoGames.TiliManager.dto.leaguedto.LeagueDeleteDTO;
import com.JPAVideoGames.TiliManager.dto.leagueteamdto.LeagueTeamCreateDTO;
import com.JPAVideoGames.TiliManager.dto.teamdto.TeamUpdateDTO;
import com.JPAVideoGames.TiliManager.exceptions.LeagueException;
import com.JPAVideoGames.TiliManager.service.LeagueService;
import jakarta.persistence.EntityNotFoundException;
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

    @GetMapping("/{id}")
    public ResponseEntity<LeagueDTO> getLeagues(@PathVariable long id) {
        return leagueService.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LeagueDTO> createLeague(@RequestBody @Valid LeagueCreateDTO leagueCreateDTO) throws LeagueException{
        return ResponseEntity.ok(leagueService.createLeague(leagueCreateDTO));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteLeague(@RequestBody @Valid LeagueDeleteDTO leagueDeleteDTO) throws LeagueException {
        try {
            leagueService.deleteLeague(leagueDeleteDTO);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            ResponseEntity.notFound();
        }
        return ResponseEntity.internalServerError().body("No se pudo borrar");
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<LeagueDTO> addTeamToLeague(@RequestBody @Valid TeamUpdateDTO teamUpdateDTO, @PathVariable Long id) throws LeagueException{
        return ResponseEntity.ok(leagueService.addTeam(teamUpdateDTO, id));
    }


}