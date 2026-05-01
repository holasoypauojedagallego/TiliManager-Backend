package com.JPAVideoGames.TiliManager.service;

import com.JPAVideoGames.TiliManager.dto.*;
import com.JPAVideoGames.TiliManager.exceptions.InvalidMoneyException;
import com.JPAVideoGames.TiliManager.exceptions.MarketException;
import com.JPAVideoGames.TiliManager.exceptions.PlayersSizeException;
import com.JPAVideoGames.TiliManager.model.Player;
import com.JPAVideoGames.TiliManager.repository.TeamRepository;
import com.JPAVideoGames.TiliManager.util.TeamMapper;
import com.JPAVideoGames.TiliManager.util.UserTiliMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final UserTiliService userTiliService;
    private final UserTiliMapper userTiliMapper;
    private final MarketService marketService;
    private final PlayerService playerService;

    public TeamService(TeamRepository teamRepository, TeamMapper teamMapper, MarketService marketService,
                       UserTiliService userTiliService, UserTiliMapper userTiliMapper, PlayerService playerService) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
        this.userTiliService = userTiliService;
        this.userTiliMapper = userTiliMapper;
        this.marketService = marketService;
        this.playerService = playerService;
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

    public Optional<TeamDTO> updateCreateTeam(TeamUpdateDTO teamUpdateDTO) throws PlayersSizeException {
        if (teamUpdateDTO.getPlayers().size() > 7 || teamUpdateDTO.getPlayers().size() < 5){
            throw new PlayersSizeException("El jugador ha de tener como máximo 7 jugadores, y como mínimo 5");
        }
        return teamRepository.findByOwner(userTiliMapper.toEntity(teamUpdateDTO.getOwner())).map(team ->{
            if (!teamUpdateDTO.getName().trim().isBlank() && teamUpdateDTO.getName() != null && !teamUpdateDTO.getName().trim().equals(team.getName())){
                team.setName(teamUpdateDTO.getName().trim());
            }
            long dineroPorJugadores = 0;
            if (teamUpdateDTO.getPlayers() != team.getPlayers()) {
                for (Player s: teamUpdateDTO.getPlayers()){
                    if (s.getTeamId() == null && team.getPlayers().size() <= 6){
                        s.setTeamId(team.getId());
                        dineroPorJugadores = dineroPorJugadores + s.getPrice();
                        team.setOnePlayer(s);
                    }
                }
            }
            if (teamUpdateDTO.getMoney() < 0 || teamUpdateDTO.getMoney() == null || (dineroPorJugadores + teamUpdateDTO.getMoney()) != team.getMoney()) {
                try {
                    throw new InvalidMoneyException("El dinero no cuadra");
                } catch (InvalidMoneyException e) {
                    throw new RuntimeException(e);
                }
            }
            team.setMoney(teamUpdateDTO.getMoney());
            return teamMapper.toDto(teamRepository.save(team));
        });
    }

    public Optional<TeamDTO> cambiarJugadoresEquipo(TeamUpdateDTO teamUpdateDTO) throws PlayersSizeException {
        if (teamUpdateDTO.getPlayers().size() > 7 || teamUpdateDTO.getPlayers().size() < 5){
            throw new PlayersSizeException("El jugador ha de tener como máximo 7 jugadores, y como mínimo 5");
        }
        return teamRepository.findByOwner(userTiliMapper.toEntity(teamUpdateDTO.getOwner())).map(team ->{
            long dineroPorJugadores = 0;
            if (teamUpdateDTO.getPlayers() != team.getPlayers()) {
                for (Player s: teamUpdateDTO.getPlayers()){
                    if (s.getTeamId() == null && team.getPlayers().size() <= 6){
                        s.setTeamId(team.getId());
                        dineroPorJugadores = dineroPorJugadores + s.getPrice();
                        team.setOnePlayer(s);
                    }
                }
            }
            if (teamUpdateDTO.getMoney() < 0 || teamUpdateDTO.getMoney() == null || (dineroPorJugadores + teamUpdateDTO.getMoney()) != team.getMoney()) {
                try {
                    throw new InvalidMoneyException("El dinero no cuadra");
                } catch (InvalidMoneyException e) {
                    throw new RuntimeException(e);
                }
            }
            team.setMoney(teamUpdateDTO.getMoney());
            return teamMapper.toDto(teamRepository.save(team));
        });
    }

    public Optional<TeamDTO> venderJugador(VenderDTO venderDTO) throws PlayersSizeException{
        if (venderDTO.getPlayer().getTeamId() == null || venderDTO.getPlayer().getTeamId() != venderDTO.getTeamUpdateDTO().getId()){return Optional.empty();}
        if (venderDTO.getTeamUpdateDTO().getPlayers().size() < 6 || venderDTO.getTeamUpdateDTO().getPlayers().size() > 7)
        {throw new PlayersSizeException("El equipo ha de tener como máximo 7 jugadores, y como mínimo 5");}
        return teamRepository.findByOwner(userTiliMapper.toEntity(venderDTO.getTeamUpdateDTO().getOwner())).map(team ->{
            Optional<Player> p = playerService.getJugador(venderDTO.getPlayer().getId());
            if (venderDTO.getTeamUpdateDTO().getMoney().longValue() != team.getMoney().longValue()
                    || p.isEmpty() || p.get().getTeamId() != team.getId() ) {
                try {
                    throw new InvalidMoneyException("El dinero no cuadra, respecto tu equipo y jugador");
                } catch (InvalidMoneyException e) {
                    throw new RuntimeException(e);
                }
            }
            if (team.getPlayers().size() < 6 || team.getPlayers().size() > 7)
            {
                try {
                    throw new PlayersSizeException("El equipo ha de tener como máximo 7 jugadores, y como mínimo 5");
                } catch (PlayersSizeException e) {
                    throw new RuntimeException(e);
                }
            }
            p.get().setTeamId(null);
            team.deleteOnePlayer(p.get());
            team.setMoney(team.getMoney() + p.get().getPrice());
            marketService.actualizarMercado(p.get());
            return teamMapper.toDto(teamRepository.save(team));
        });
    }

    public Optional<TeamDTO> comprarJugador(VenderDTO venderDTO) throws PlayersSizeException{
        if (venderDTO.getPlayer().getTeamId() != null || !marketService.getMercadoDTO().getFichable()){return Optional.empty();}
        if (venderDTO.getTeamUpdateDTO().getPlayers().size() < 5 || venderDTO.getTeamUpdateDTO().getPlayers().size() > 6)
        {throw new PlayersSizeException("El equipo ha de tener como máximo 7 jugadores, y como mínimo 5");}
        return teamRepository.findByOwner(userTiliMapper.toEntity(venderDTO.getTeamUpdateDTO().getOwner())).map(team ->{
            Optional<Player> p = playerService.getJugador(venderDTO.getPlayer().getId());
            if (venderDTO.getTeamUpdateDTO().getMoney().longValue() != team.getMoney().longValue() || p.isEmpty()
                    || p.get().getPrice() > team.getMoney() || p.get().getTeamId() != null) {
                try {
                    throw new InvalidMoneyException("El dinero no cuadra, respecto al jugador y al equipo");
                } catch (InvalidMoneyException e) {
                    throw new RuntimeException(e);
                }
            }
            if (team.getPlayers().size() < 5 || team.getPlayers().size() > 6)
            {
                try {
                    throw new PlayersSizeException("El equipo ha de tener como máximo 7 jugadores, y como mínimo 5");
                } catch (PlayersSizeException e) {
                    throw new RuntimeException(e);
                }
            }
            boolean vayaCodigoSpaghetti = true;
            for (Player t : marketService.getMercadoDTO().getPlayers()){
                if (t.getId() == p.get().getId()) {
                    vayaCodigoSpaghetti = false;
                    break;
                }
            }
            if (vayaCodigoSpaghetti){
                try {
                    throw new MarketException("Este jugador no esta para comprar en el mercado");
                } catch (MarketException e) {
                    throw new RuntimeException(e);
                }
            }
            p.get().setTeamId(team.getId());
            team.setOnePlayer(p.get());
            marketService.actualizarMercado(p.get());
            team.setMoney(team.getMoney() - p.get().getPrice());
            return teamMapper.toDto(teamRepository.save(team));
        });
    }
}
