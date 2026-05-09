package com.JPAVideoGames.TiliManager.service;

import com.JPAVideoGames.TiliManager.dto.leaguedto.LeagueCreateDTO;
import com.JPAVideoGames.TiliManager.dto.leaguedto.LeagueDTO;
import com.JPAVideoGames.TiliManager.dto.leaguedto.LeagueDeleteDTO;
import com.JPAVideoGames.TiliManager.dto.matchdto.PartidoEncapsuladoDTO;
import com.JPAVideoGames.TiliManager.dto.usertilidto.UserTiliPassDTO;
import com.JPAVideoGames.TiliManager.exceptions.LeagueException;
import com.JPAVideoGames.TiliManager.model.*;
import com.JPAVideoGames.TiliManager.repository.LeagueRepository;
import com.JPAVideoGames.TiliManager.util.LeagueMapper;
import com.JPAVideoGames.TiliManager.util.PartidoEncapsuladoMapper;
import com.JPAVideoGames.TiliManager.util.TeamMapper;
import com.JPAVideoGames.TiliManager.util.UserTiliMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Lazy
@Transactional
public class LeagueService {

    @Autowired
    @Lazy
    private UserTiliService userTiliService;

    @Autowired
    @Lazy
    private TeamService teamService;

    @Autowired
    @Lazy
    private MatchService matchService;

    @Autowired
    @Lazy
    private MarketService marketService;

    @Autowired
    @Lazy
    private LeagueTeamService leagueTeamService;


    @Autowired
    @Lazy
    private PlayerLeagueService playerLeagueService;

    @Autowired
    @Lazy
    private LeagueMapper leagueMapper;

    @Autowired
    @Lazy
    private UserTiliMapper userTiliMapper;

    @Autowired
    @Lazy
    private TeamMapper teamMapper;

    @Autowired
    @Lazy
    private PartidoEncapsuladoMapper partidoEncapsuladoMapper;

    private final LeagueRepository leagueRepository;

    public LeagueService(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    public List<LeagueDTO> getAll(){
        return leagueMapper.toDTO(leagueRepository.findAll());
    }

    public long countAll(){
        return leagueRepository.count();
    }

    public Optional<LeagueDTO> getById(long id){
        return leagueRepository.findById(id).map(leagueMapper::toDTO);
    }

    public LeagueDTO createLeague(LeagueCreateDTO leagueCreateDTO) throws LeagueException{
        long ligasPorUser = leagueRepository.countByOwner(userTiliMapper.toEntity(leagueCreateDTO.getOwner()));
        if (ligasPorUser >= 3) {
            throw new LeagueException("Solo se permiten un máximo de 3 ligas por usuario");
        }
        League league = leagueRepository.save(leagueMapper.toCreateEntity(leagueCreateDTO));
        playerLeagueService.createJugadoresLeague(league);
        marketService.anadir(league.getId());
        return leagueMapper.toDTO(league);
    }

    public void deleteLeague(LeagueDeleteDTO leagueDeleteDTO) throws LeagueException {
        // Encuentra el dueño por Dueño y ID, para evitar errores y ser bien concretos (con ciertos mapper para pasarlo a entidad de forma más fácil)
        Optional<League> league = leagueRepository.findByOwnerAndId(userTiliMapper.toEntity(leagueDeleteDTO.getOwner()), leagueDeleteDTO.getId());
        if (league.isEmpty()) {throw new LeagueException("No se puede borrar porque no se encuentra la liga");}
        if (!(league.get().getOwner().getId().equals(leagueDeleteDTO.getOwner().getId())) ||
                !(league.get().getOwner().getName().equals(leagueDeleteDTO.getOwner().getName())) ||
                !(league.get().getOwner().getEmail().equals(leagueDeleteDTO.getOwner().getEmail()))) {
            throw new LeagueException("El dueño, no es el verdadero dueño");
        }
        // Comentario para que Rusben no llore, dos early returns, el primero es por si la liga no existe, lanze Exception, y el segundo,
        // es por si acaso alguien intenta borrar con su cuenta la liga de otro usuario, que pete si el dueño no eres tú vaya
        leagueRepository.deleteById(league.get().getId()); // Esto es sencillo, deleteById, y le doy el id poco más
    }

    public LeagueDTO addTeam(UserTiliPassDTO userTiliPassDTO, Long id) throws LeagueException{
        Optional<League> liga = leagueRepository.findById(id);
        Optional<UserTili> userTili = userTiliService.getByIdConfirmacion(userTiliPassDTO.getId());
        if (liga.isEmpty()){
            throw new LeagueException("No es posible unirse a la liga");
        }
        if (userTili.isEmpty()){
            throw new LeagueException("Usuario erroneo");
        }
        if (liga.get().isClosed()) {
            throw new LeagueException("La liga es privada");
        }
        if (liga.get().getTeams().size() >= 20){throw new IllegalArgumentException("Max of 20 teams allowed");}

        Team teamFromUserTili = teamService.guardarEquipoPrimero(userTili.get());

        LeagueTeam leagueTeam = new LeagueTeam();
        leagueTeam.setTeam(teamFromUserTili);
        leagueTeam.setLeague(liga.get());

        liga.get().setOneTeam(leagueTeam);
        return leagueMapper.toDTO(leagueRepository.save(liga.get()));
    }

    public List<PartidoEncapsuladoDTO> playMatch(UserTiliPassDTO userTiliPassDTO, Long id) throws LeagueException{
        Optional<League> liga = leagueRepository.findById(id);
        if (liga.isEmpty()){
            throw new LeagueException("No existe esa liga");
        }
        if (!(liga.get().getTeams().stream().filter(t->t.getTeam().getPlayers().size() >= 5).count() >= 2)) throw new LeagueException("No hay suficientes jugadores en la liga");
        LeagueTeam leagueTeamUser = new LeagueTeam();
        for (LeagueTeam lTeam: liga.get().getTeams()) {
            if (lTeam.getTeam().getOwner().getId().equals(userTiliPassDTO.getId())) {
                leagueTeamUser = lTeam;
            }
        }
        if (leagueTeamUser.getTeam() == null) throw new LeagueException("No existe ese usuario con equipo en esta liga");
        boolean t = false;
        LeagueTeam rival = liga.get().getTeams().get((int)(Math.random() * liga.get().getTeams().size()));
        while (!t) {
            if (rival.getId() == leagueTeamUser.getId() || rival.getTeam().getPlayers().size() < 5) rival = liga.get().getTeams().get((int)(Math.random() * liga.get().getTeams().size()));
            else t = true;
        }
        Match match = matchService.codigo(leagueTeamUser.getTeam(), rival.getTeam(), liga);
        match.setLeague(liga.get());
        if (match.getLocalTeamGoals() > match.getVisitorTeamGoals()) {
            leagueTeamUser.setWins(leagueTeamUser.getWins() + 1);
            rival.setLosses(rival.getLosses() + 1);
        } else if (match.getVisitorTeamGoals() > match.getLocalTeamGoals()) {
            rival.setWins(rival.getWins() + 1);
            leagueTeamUser.setLosses(leagueTeamUser.getLosses() + 1);
        } else {
            leagueTeamUser.setDraws(leagueTeamUser.getDraws() + 1);
            rival.setDraws(rival.getDraws() + 1);
        }
        leagueRepository.save(liga.get());
        return partidoEncapsuladoMapper.toDTO(match.getPartidoEncapsulado());
    }

}
