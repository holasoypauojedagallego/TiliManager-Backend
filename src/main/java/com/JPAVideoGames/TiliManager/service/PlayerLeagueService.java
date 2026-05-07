package com.JPAVideoGames.TiliManager.service;

import com.JPAVideoGames.TiliManager.dto.playerleague.PlayerLeagueDTO;
import com.JPAVideoGames.TiliManager.model.League;
import com.JPAVideoGames.TiliManager.model.Player;
import com.JPAVideoGames.TiliManager.model.PlayerLeague;
import com.JPAVideoGames.TiliManager.repository.PlayerLeagueRepository;
import com.JPAVideoGames.TiliManager.util.PlayerLeagueMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Lazy
@Transactional
public class PlayerLeagueService {

    @Autowired
    @Lazy
    private PlayerLeagueMapper playerLeagueMapper;

    @Autowired
    @Lazy
    private PlayerService playerService;

    private final PlayerLeagueRepository playerLeagueRepository;

    public PlayerLeagueService(PlayerLeagueRepository playerRepository) {
        this.playerLeagueRepository = playerRepository;
    }

    public List<PlayerLeagueDTO> getJugadores() {
        return playerLeagueMapper.toDTO(playerLeagueRepository.findAll());
    }

    public Optional<PlayerLeagueDTO> getJugador(long id) {
        return playerLeagueRepository.findById(id).map(playerLeagueMapper::toDTO);
    }

    public Optional<PlayerLeague> getJugadorPuro(long id) {
        return playerLeagueRepository.findById(id);
    }

    public List<PlayerLeagueDTO> getJugadoresByTeamIdNull() {
        return playerLeagueMapper.toDTO(playerLeagueRepository.findAllByTeamId(null));
    }

    public List<PlayerLeagueDTO> getJugadoresByLeague(long id) {
        return playerLeagueMapper.toDTO(playerLeagueRepository.findAllByLeagueId(id));
    }

    public List<PlayerLeagueDTO> getJugadoresByLeagueAndTeamId(Long leagueId, Long teamId) {
        return playerLeagueMapper.toDTO(playerLeagueRepository.findByLeagueIdAndTeamId(leagueId, teamId));
    }

    public List<PlayerLeagueDTO> getJugadoresByLeagueAndTeamIdNull(Long leagueId) {
        return playerLeagueMapper.toDTO(playerLeagueRepository.findByLeagueIdAndTeamId(leagueId, null));
    }

    public void createJugadoresLeague(League league){
        List<Player> players = playerService.getJugadores();
        for(Player player : players){
            PlayerLeague playerLeague = new PlayerLeague();
            playerLeague.setPlayer(player);
            playerLeague.setLeague(league);
            playerLeague.setTeamId(null);
            playerLeagueRepository.save(playerLeague);
        }
    }

}