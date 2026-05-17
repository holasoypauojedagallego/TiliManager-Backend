package com.JPAVideoGames.TiliManager.service;

import com.JPAVideoGames.TiliManager.dto.leaguedto.LeagueCreateDTO;
import com.JPAVideoGames.TiliManager.dto.leaguedto.LeagueDTO;
import com.JPAVideoGames.TiliManager.dto.leaguedto.LeagueDeleteDTO;
import com.JPAVideoGames.TiliManager.dto.matchdto.PartidoEncapsuladoDTO;
import com.JPAVideoGames.TiliManager.dto.usertilidto.UserTiliPassDTO;
import com.JPAVideoGames.TiliManager.exceptions.LeagueException;
import com.JPAVideoGames.TiliManager.model.*;
import com.JPAVideoGames.TiliManager.repository.AdminRepository;
import com.JPAVideoGames.TiliManager.repository.LeagueRepository;
import com.JPAVideoGames.TiliManager.util.LeagueMapper;
import com.JPAVideoGames.TiliManager.util.PartidoEncapsuladoMapper;
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
    private AdminRepository adminRepository;

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
        adminRepository.save(new Admin(("La liga: " + league.getName() + ", ha sido creada"), league.getId()));
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
        int e = league.get().getTeams().size(); // Esto, 1: Primero guardo cuantos Equipos hay en una variable
        for (int i = 0; i < e; i++) { // Y después en el fori, las voy borrando
            deleteTeam(leagueDeleteDTO.getOwner(), league.get().getId(), league.get().getTeams().get(0).getId()); // Pero pongo get(0), porque al irse borrando, la primera va desapareciendo siempre, es por eso que no hago un get(i)
        }
        matchService.borrarLigaPartdios(league.get().getId());
        // Comentario para que Rusben no llore, dos early returns, el primero es por si la liga no existe, lanze Exception, y el segundo,
        // es por si acaso alguien intenta borrar con su cuenta la liga de otro usuario, que pete si el dueño no eres tú vaya
        leagueRepository.deleteById(league.get().getId()); // Esto es sencillo, deleteById, y le doy el id poco más
        adminRepository.save(new Admin(("La liga: " + league.get().getName() + ", ha sido borrada"), league.get().getOwner().getId()));
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
        if (liga.get().getTeams().size() >= 20){throw new IllegalArgumentException("Max of 20 equipos permitidos");}

        Team teamFromUserTili = teamService.guardarEquipoPrimero(userTili.get());

        LeagueTeam leagueTeam = new LeagueTeam();
        leagueTeam.setTeam(teamFromUserTili);
        leagueTeam.setLeague(liga.get());

        liga.get().setOneTeam(leagueTeam);
        adminRepository.save(new Admin(("La liga: " + liga.get().getName() + ", ha añadido el equipoo: " + teamFromUserTili.getName() + " a la liga"), liga.get().getId()));
        return leagueMapper.toDTO(leagueRepository.save(liga.get()));
    }

    public LeagueDTO deleteTeam(UserTiliPassDTO userTiliPassDTO, Long id, Long idTeam) throws LeagueException{
        Optional<UserTili> userTili = userTiliService.getByIdConfirmacion(userTiliPassDTO.getId());
        if (userTili.isEmpty()){
            throw new LeagueException("Usuario erroneo");
        }
        Optional<League> liga = leagueRepository.findByOwnerAndId(userTili.get(), id);
        if (liga.isEmpty()){
            throw new LeagueException("Liga de usuario no encontrada");
        }
        if (liga.get().getTeams().isEmpty()){throw new IllegalArgumentException("No hay equipos que borrar");}

        Optional<LeagueTeam> equipoLiga = leagueTeamService.getById(idTeam);
        if (equipoLiga.isEmpty()){throw new LeagueException("Equipo de Liga no encontrada");}

        equipoLiga.get().getTeam().getPlayers().forEach(player -> player.setTeamId(null));
        equipoLiga.get().getTeam().getPlayers().clear();
        equipoLiga.get().getTeam().setLeagueTeam(null);
        liga.get().getTeams().remove(equipoLiga.get());
        // Orgulloso de decir que este código funciona

        leagueTeamService.delete(equipoLiga.get().getId());
        adminRepository.save(new Admin(("La liga: " + liga.get().getName() + ", ha eliminado al equipo: " + equipoLiga.get().getTeam().getName() + " de la liga"), liga.get().getId()));
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
            leagueTeamUser.getTeam().setMoney(leagueTeamUser.getTeam().getMoney() + 1000000);
            rival.setLosses(rival.getLosses() + 1);
        } else if (match.getVisitorTeamGoals() > match.getLocalTeamGoals()) {
            rival.setWins(rival.getWins() + 1);
            rival.getTeam().setMoney(rival.getTeam().getMoney() + 1000000);
            leagueTeamUser.setLosses(leagueTeamUser.getLosses() + 1);
        } else {
            leagueTeamUser.setDraws(leagueTeamUser.getDraws() + 1);
            leagueTeamUser.getTeam().setMoney(leagueTeamUser.getTeam().getMoney() + 400000);
            rival.getTeam().setMoney(rival.getTeam().getMoney() + 400000);
            rival.setDraws(rival.getDraws() + 1);
        }
        leagueTeamUser.setGoalsScored(leagueTeamUser.getGoalsScored() + match.getLocalTeamGoals());
        leagueTeamUser.setGoalsReceived(leagueTeamUser.getGoalsReceived() + match.getVisitorTeamGoals());
        rival.setGoalsScored(rival.getGoalsScored() + match.getVisitorTeamGoals());
        rival.setGoalsReceived(rival.getGoalsReceived() + match.getLocalTeamGoals());
        leagueRepository.save(liga.get());
        return partidoEncapsuladoMapper.toDTO(match.getPartidoEncapsulado());
    }

}
