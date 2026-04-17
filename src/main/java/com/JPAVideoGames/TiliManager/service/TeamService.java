package com.JPAVideoGames.TiliManager.service;

import com.JPAVideoGames.TiliManager.dto.*;
import com.JPAVideoGames.TiliManager.model.Team;
import com.JPAVideoGames.TiliManager.repository.TeamRepository;
import com.JPAVideoGames.TiliManager.util.TeamMapper;
import com.JPAVideoGames.TiliManager.util.UserTiliMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final UserTiliService userTiliService;
    private final UserTiliMapper userTiliMapper;

    public TeamService(TeamRepository teamRepository, TeamMapper teamMapper,
                       UserTiliService userTiliService, UserTiliMapper userTiliMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
        this.userTiliService = userTiliService;
        this.userTiliMapper = userTiliMapper;
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

    public Optional<TeamDTO> getTeamByOwner(UserTiliPassDTO userTiliPassDTO){
        Optional<UserTiliDTO> userTiliPrueba = userTiliService.getById(userTiliPassDTO.getId());
        if (userTiliPrueba.isEmpty()){
            return Optional.empty();
        }
        return teamRepository.findByOwner(userTiliMapper.toEntity(userTiliPassDTO)).map(teamMapper::toDto);
    }

    public Optional<TeamDTO> updateTeam(TeamUpdateDTO teamUpdateDTO){
        if (teamRepository.findById(teamUpdateDTO.getId()).isEmpty()){
            return Optional.empty();
        }
        Team team = teamRepository.findById(teamUpdateDTO.getId()).get();
        if (!teamUpdateDTO.getName().isBlank() && !teamUpdateDTO.getName().equals(team.getName())){
            team.setName(teamUpdateDTO.getName());
        }
        if (teamUpdateDTO.getPrice() < 0 && teamUpdateDTO.getPrice() == team.getPrice()) {
            team.setPrice(teamUpdateDTO.getPrice());
        }
        // team.setJugadores() todo ACABAR ESTO!!!!
    }

}
