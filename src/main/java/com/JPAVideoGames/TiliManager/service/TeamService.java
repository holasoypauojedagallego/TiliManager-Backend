package com.JPAVideoGames.TiliManager.service;

import com.JPAVideoGames.TiliManager.dto.marketdto.MercadoDTO;
import com.JPAVideoGames.TiliManager.dto.playerleague.PlayerLeagueDTO;
import com.JPAVideoGames.TiliManager.dto.teamdto.TeamDTO;
import com.JPAVideoGames.TiliManager.dto.teamdto.TeamUpdateDTO;
import com.JPAVideoGames.TiliManager.dto.teamdto.VenderDTO;
import com.JPAVideoGames.TiliManager.dto.usertilidto.UserTiliDTO;
import com.JPAVideoGames.TiliManager.dto.usertilidto.UserTiliPassDTO;
import com.JPAVideoGames.TiliManager.exceptions.*;
import com.JPAVideoGames.TiliManager.model.*;
import com.JPAVideoGames.TiliManager.repository.AdminRepository;
import com.JPAVideoGames.TiliManager.repository.TeamRepository;
import com.JPAVideoGames.TiliManager.util.PlayerLeagueMapper;
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
    private final PlayerLeagueService playerLeagueService;
    private final PlayerLeagueMapper playerLeagueMapper;
    private final AdminRepository adminRepository;

    public TeamService(TeamRepository teamRepository, TeamMapper teamMapper, MarketService marketService,
                       PlayerLeagueMapper playerLeagueMapper, AdminRepository adminRepository,
                       UserTiliService userTiliService, UserTiliMapper userTiliMapper, PlayerLeagueService playerLeagueService) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
        this.userTiliService = userTiliService;
        this.userTiliMapper = userTiliMapper;
        this.marketService = marketService;
        this.playerLeagueService = playerLeagueService;
        this.playerLeagueMapper = playerLeagueMapper;
        this.adminRepository = adminRepository;
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

    public List<TeamDTO> getTeamByRole(UserTiliRole userTiliRole){
        return teamMapper.toDto(teamRepository.findAllByOwnerRole(userTiliRole));
    }

    public Optional<TeamDTO> getTeamByOwner(UserTiliPassDTO userTiliPassDTO){
        Optional<UserTiliDTO> userTiliPrueba = userTiliService.getById(userTiliPassDTO.getId());
        if (userTiliPrueba.isEmpty()){
            return Optional.empty();
        }
        return teamRepository.findByOwner(userTiliMapper.toEntity(userTiliPassDTO)).map(teamMapper::toDto);
    }

    public TeamDTO searchRivalTeam(long localteamId) throws TeamException {
        List<Team> equipos = teamRepository.findAll();
        if (equipos.size() <= 2) {
            throw new TeamException("No hay suficientes equipos disponibles");
        }
        boolean equipoEscogible = false;
        Team visitorTeam = equipos.get((int) (Math.random() * equipos.size()));
        while (!equipoEscogible) {
            if (visitorTeam.getId() == localteamId || visitorTeam.getOwner().getRole() == UserTiliRole.BOT || visitorTeam.getOwner().getRole() == UserTiliRole.ADMIN) {
                visitorTeam = equipos.get((int) (Math.random() * equipos.size()));
            } else  {
                equipoEscogible = true;
            }
        }
        return teamMapper.toDto(visitorTeam);
    }

    public Optional<TeamDTO> updateCreateTeam(TeamUpdateDTO teamUpdateDTO) throws PlayersSizeException {
        adminRepository.save(new Admin(("El usuario " + teamUpdateDTO.getOwner().getEmail() + ", esta intentando crear e actualizar el equipo: " + teamUpdateDTO.getName() + " en el teamService"), teamUpdateDTO.getOwner().getId()));
        if (teamUpdateDTO.getPlayers().size() > 7 || teamUpdateDTO.getPlayers().size() < 5){
            throw new PlayersSizeException("El jugador ha de tener como máximo 7 jugadores, y como mínimo 5");
        }
        return teamRepository.findByOwnerAndLeagueTeamId(userTiliMapper.toEntity(teamUpdateDTO.getOwner()), teamUpdateDTO.getLeagueTeam().getId()).map(team ->{
            if (!teamUpdateDTO.getName().trim().isBlank() && teamUpdateDTO.getName() != null && !teamUpdateDTO.getName().trim().equals(team.getName())){
                team.setName(teamUpdateDTO.getName().trim());
            }
            long dineroPorJugadores = 0;
            boolean equipoNoIgual = false;
            if (!teamUpdateDTO.getPlayers().isEmpty() && !team.getPlayers().isEmpty() && teamUpdateDTO.getPlayers().size() == team.getPlayers().size()) {
                for (int i = 0; teamUpdateDTO.getPlayers().size() > i; i++) {
                    if (teamUpdateDTO.getPlayers().get(i).getId() != team.getPlayers().get(i).getId() ||
                            teamUpdateDTO.getPlayers().size() != team.getPlayers().size()) {
                        equipoNoIgual = true;
                        break;
                    }
                }
            } else {equipoNoIgual = true;}
            if (equipoNoIgual) {
                for (PlayerLeagueDTO s: teamUpdateDTO.getPlayers()){
                    Optional<PlayerLeague> p = playerLeagueService.getJugadorPuro(s.getId());
                    if (p.isPresent() && p.get().getTeamId() == null && team.getPlayers().size() <= 6){
                        p.get().setTeamId(team.getId());
                        dineroPorJugadores = dineroPorJugadores + p.get().getPlayer().getPrice();
                        marketService.actualizarMercado(playerLeagueMapper.toDTO(p.get()));
                        team.setOnePlayer(p.get());
                    } else {
                        try {
                            throw new PlayersSizeException("Este jugador no se puede comprar");
                        } catch (PlayersSizeException e) {
                            throw new RuntimeException(e);
                        }
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
            adminRepository.save(new Admin(("El usuario " + teamUpdateDTO.getOwner().getEmail() + ", ha creado e actualizado el equipo: " + teamUpdateDTO.getName() + " en el teamService"), teamUpdateDTO.getOwner().getId()));
            return teamMapper.toDto(teamRepository.save(team));
        });
    }

    public Optional<TeamDTO> venderJugador(VenderDTO venderDTO) throws PlayersSizeException{
        if (venderDTO.getPlayer().getTeam() == null || venderDTO.getPlayer().getTeam() != venderDTO.getTeamUpdateDTO().getId()){return Optional.empty();}
        if (venderDTO.getTeamUpdateDTO().getPlayers().size() < 6 || venderDTO.getTeamUpdateDTO().getPlayers().size() > 7)
        {throw new PlayersSizeException("El equipo ha de tener como máximo 7 jugadores, y como mínimo 5");}
        return teamRepository.findByOwner(userTiliMapper.toEntity(venderDTO.getTeamUpdateDTO().getOwner())).map(team ->{
            Optional<PlayerLeague> p = playerLeagueService.getJugadorPuro(venderDTO.getPlayer().getId());
            // Early return por si no cuadran los equipo, o por si te vas a quedar con menos de 5 jugadores
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
            team.setMoney(team.getMoney() + p.get().getPlayer().getPrice());
            marketService.actualizarMercado(playerLeagueMapper.toDTO(p.get()));
            Admin admin = new Admin();
            admin.setLog("El usuario" + team.getOwner().getName() + ", con id: " + team.getOwner().getId() + ", para el equipo: " + team + ", ha vendido el jugador " + p.get());
            adminRepository.save(admin);
            return teamMapper.toDto(teamRepository.save(team));
        });
    }

    public Optional<TeamDTO> comprarJugador(VenderDTO venderDTO) throws PlayersSizeException{
        if (venderDTO.getPlayer().getTeam() != null){return Optional.empty();}
        if (venderDTO.getTeamUpdateDTO().getPlayers().size() < 5 || venderDTO.getTeamUpdateDTO().getPlayers().size() > 6)
        {throw new PlayersSizeException("El equipo ha de tener como máximo 7 jugadores, y como mínimo 5");}
        return teamRepository.findByOwner(userTiliMapper.toEntity(venderDTO.getTeamUpdateDTO().getOwner())).map(team ->{
            Optional<PlayerLeague> p = playerLeagueService.getJugadorPuro(venderDTO.getPlayer().getId());
            if (venderDTO.getTeamUpdateDTO().getMoney().longValue() != team.getMoney().longValue() || p.isEmpty()
                    || p.get().getPlayer().getPrice() > team.getMoney() || p.get().getTeamId() != null) {
                try {
                    throw new InvalidMoneyException("El dinero no cuadra, respecto al jugador y al equipo");
                } catch (InvalidMoneyException e) {
                    throw new RuntimeException(e);
                }
            }
            if (team.getPlayers().size() < 5 || team.getPlayers().size() > 6) {
                try {
                    throw new PlayersSizeException("El equipo ha de tener como máximo 7 jugadores, y como mínimo 5");
                } catch (PlayersSizeException e) {
                    throw new RuntimeException(e);
                }
            }
            if (team.getLeagueTeam().getLeague().getId() != p.get().getLeague().getId()){
                try {
                    throw new LeagueException("Ese jugador no pertenece a la misma liga que el equipo");
                } catch (LeagueException e) {
                    throw new RuntimeException(e);
                }
            }
            boolean vayaCodigoSpaghetti = true;
            for (MercadoDTO t : marketService.getMercados()){
                for (PlayerLeagueDTO pl : t.getPlayers()) {
                    if (pl.getId() == p.get().getId() && t.getFichable()) {
                        vayaCodigoSpaghetti = false;
                        break;
                    }
                }
            }
            if (vayaCodigoSpaghetti){
                try {
                    throw new MarketException("Este jugador no esta para comprar en el mercado, mercado cerrado");
                } catch (MarketException e) {
                    throw new RuntimeException(e);
                }
            }
            p.get().setTeamId(team.getId());
            team.setOnePlayer(p.get());
            marketService.actualizarMercado(playerLeagueMapper.toDTO(p.get()));
            team.setMoney(team.getMoney() - p.get().getPlayer().getPrice());
            Admin admin = new Admin();
            admin.setLog("El usuario" + team.getOwner().getName() + ", con id: " + team.getOwner().getId() + ", para el equipo: " + team + ", ha comprado el jugador " + p.get());
            adminRepository.save(admin);
            return teamMapper.toDto(teamRepository.save(team));
        });
    }

    public void dineroPorResultado(TeamUpdateDTO localteamDTO, int diferencia) {
        teamRepository.findByOwner(userTiliMapper.toEntity(localteamDTO.getOwner())).map(team ->  {
            if (diferencia > 0) {
                team.setMoney(team.getMoney() + 100000);
            } else if (diferencia < 0) {
                if (team.getMoney() - 100000 < 0) {
                    team.setMoney(0L);
                } else {
                    team.setMoney(team.getMoney() - 100000);
                }

            }
            return teamRepository.save(team);
        });
    }

    public Team guardarEquipoPrimero(UserTili userTili) {
        Team teamFromUserTili = new Team();
        int numeroaleatorio = (int) (Math.random() * 100000000);
        teamFromUserTili.setName("T_" + numeroaleatorio);
        teamFromUserTili.setMoney(133000000L);
        teamFromUserTili.setOwner(userTili);
        return teamRepository.save(teamFromUserTili);
    }
}
