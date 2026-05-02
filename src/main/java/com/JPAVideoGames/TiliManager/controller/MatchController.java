package com.JPAVideoGames.TiliManager.controller;

import com.JPAVideoGames.TiliManager.dto.MatchDTO;
import com.JPAVideoGames.TiliManager.dto.PartidoEncapsuladoDTO;
import com.JPAVideoGames.TiliManager.dto.TeamDTO;
import com.JPAVideoGames.TiliManager.dto.TeamUpdateDTO;
import com.JPAVideoGames.TiliManager.exceptions.TeamException;
import com.JPAVideoGames.TiliManager.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/partidos")
public class MatchController {

    @Autowired
    @Lazy
    private MatchService matchService;

    @PostMapping
    public ResponseEntity<List<PartidoEncapsuladoDTO>> codigoJugar(@RequestBody TeamUpdateDTO localTeam) throws TeamException {
        return ResponseEntity.ok(matchService.empezarCodigo(localTeam));
    }

    @PostMapping("/t1")
    public ResponseEntity<List<PartidoEncapsuladoDTO>> codigoTorneoSimulado(@RequestBody TeamUpdateDTO teamDTO) {
        return ResponseEntity.ok(matchService.torneoSimuladoP1(teamDTO));
    }

    @GetMapping("/history")
    public ResponseEntity<List<MatchDTO>> getPartidos() {
        return ResponseEntity.ok(matchService.getPartidos());
    }

    @GetMapping("/history/{id}")
    public ResponseEntity<MatchDTO> getPartidoById(@PathVariable long id) {
        return matchService.getPartidoById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


}