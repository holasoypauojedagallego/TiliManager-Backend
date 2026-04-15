package com.JPAVideoGames.TiliManager.service;

import com.JPAVideoGames.TiliManager.model.Team;
import com.JPAVideoGames.TiliManager.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> getTeams() {
        return teamRepository.findAll();
    }

    public Optional<Team> getTeamById(Long id){
        return teamRepository.findById(id);
    }

    public Optional<Team> getTeamByName(String name){
        return teamRepository.findByName(name);
    }
}
