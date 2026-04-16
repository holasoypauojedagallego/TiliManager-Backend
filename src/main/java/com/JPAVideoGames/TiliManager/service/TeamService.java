package com.JPAVideoGames.TiliManager.service;

import com.JPAVideoGames.TiliManager.dto.TeamDTO;
import com.JPAVideoGames.TiliManager.model.Team;
import com.JPAVideoGames.TiliManager.repository.TeamRepository;
import com.JPAVideoGames.TiliManager.util.TeamMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    public TeamService(TeamRepository teamRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    public List<TeamDTO> getTeams() {
        return teamMapper.toDto(teamRepository.findAll());
    }

    public Optional<TeamDTO> getTeamById(Long id){
        return teamRepository.findById(id).map(teamMapper::toDto);
    }

    public Optional<TeamDTO> getTeamByName(String name){
        return teamRepository.findByName(name).map(teamMapper::toDto);
    }
}
