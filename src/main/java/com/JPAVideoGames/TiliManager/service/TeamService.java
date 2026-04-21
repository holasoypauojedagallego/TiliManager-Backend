package com.JPAVideoGames.TiliManager.service;

import com.JPAVideoGames.TiliManager.dto.*;
import com.JPAVideoGames.TiliManager.exceptions.PlayersSizeException;
import com.JPAVideoGames.TiliManager.model.Player;
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

    public Optional<TeamDTO> updateTeam(TeamUpdateDTO teamUpdateDTO) throws PlayersSizeException {
        if (teamUpdateDTO.getPlayers().size() > 7 || teamUpdateDTO.getPlayers().size() < 5){
            throw new PlayersSizeException("El jugador ha de tener como máximo 7 jugadores, y como mínimo 5");
        }
        return teamRepository.findByOwner(userTiliMapper.toEntity(teamUpdateDTO.getOwner())).map(team ->{
            if (!teamUpdateDTO.getName().trim().isBlank() && teamUpdateDTO.getName() != null && !teamUpdateDTO.getName().trim().equals(team.getName())){
                team.setName(teamUpdateDTO.getName().trim());
            }
            if (teamUpdateDTO.getMoney() >= 0 && teamUpdateDTO.getMoney() != null && !Objects.equals(teamUpdateDTO.getMoney(), team.getMoney())) {
                team.setMoney(teamUpdateDTO.getMoney());
            }
            if (teamUpdateDTO.getPlayers() != team.getPlayers()) {
                for (Player s: teamUpdateDTO.getPlayers()){
                    if (s.getTeamId() == null && team.getPlayers().size() <= 6){
                        s.setTeamId(team.getId());
                        team.setOnePlayer(s);
                    }
                }
            }
            return teamMapper.toDto(teamRepository.save(team));
        });
    }

    public Optional<TeamDTO> venderJugador(TeamUpdateDTO teamDTO, Player player){
        if (player.getTeamId() == null || player.getTeamId() != teamDTO.getId()){return Optional.empty();}
        return teamRepository.findByOwner(userTiliMapper.toEntity(teamDTO.getOwner())).map(team ->{
            player.setTeamId(null);
            team.deleteOnePlayer(player);
            return teamMapper.toDto(teamRepository.save(team));
        });
    }
}
