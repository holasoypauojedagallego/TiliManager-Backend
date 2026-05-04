package com.JPAVideoGames.TiliManager.controller;

import com.JPAVideoGames.TiliManager.dto.TeamUpdateDTO;
import com.JPAVideoGames.TiliManager.exceptions.TeamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ligas")
public class LeagueController {

    @Autowired
    @Lazy

    @GetMapping
    public void getLeagues() {

    }

    @PostMapping
    public void createLeague(@RequestBody TeamUpdateDTO localTeam) throws TeamException {

    }


}