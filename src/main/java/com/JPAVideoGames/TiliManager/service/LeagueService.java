package com.JPAVideoGames.TiliManager.service;

import com.JPAVideoGames.TiliManager.dto.leaguedto.LeagueCreateDTO;
import com.JPAVideoGames.TiliManager.dto.leaguedto.LeagueDTO;
import com.JPAVideoGames.TiliManager.dto.leaguedto.LeagueDeleteDTO;
import com.JPAVideoGames.TiliManager.dto.leagueteamdto.LeagueTeamCreateDTO;
import com.JPAVideoGames.TiliManager.dto.teamdto.TeamDTO;
import com.JPAVideoGames.TiliManager.dto.teamdto.TeamUpdateDTO;
import com.JPAVideoGames.TiliManager.exceptions.LeagueException;
import com.JPAVideoGames.TiliManager.model.League;
import com.JPAVideoGames.TiliManager.model.LeagueTeam;
import com.JPAVideoGames.TiliManager.repository.LeagueRepository;
import com.JPAVideoGames.TiliManager.util.LeagueMapper;
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
    private TeamService teamService;

    @Autowired
    @Lazy
    private LeagueMapper leagueMapper;

    @Autowired
    @Lazy
    private UserTiliMapper userTiliMapper;

    @Autowired
    @Lazy
    private TeamMapper teamMapper;

    private final LeagueRepository leagueRepository;

    public LeagueService(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    public List<LeagueDTO> getAll(){
        return leagueMapper.toDTO(leagueRepository.findAll());
    }

    public LeagueDTO createLeague(LeagueCreateDTO leagueCreateDTO) throws LeagueException{
        long ligasPorUser = leagueRepository.countByOwner(userTiliMapper.toEntity(leagueCreateDTO.getOwner()));
        if (ligasPorUser >= 5) {
            throw new LeagueException("Solo se permiten un máximo de 5 ligas por usuario");
        }
        return leagueMapper.toDTO(leagueRepository.save(leagueMapper.toCreateEntity(leagueCreateDTO)));
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

    public LeagueDTO addTeam(TeamUpdateDTO teamUpdateDTO, Long id) throws LeagueException{
        Optional<League> liga = leagueRepository.findById(id);
        Optional<TeamDTO> team = teamService.getTeamByOwner(teamUpdateDTO.getOwner());
        if (liga.isEmpty()){
            throw new LeagueException("No es posible unirse a la liga");
        }
        if (team.isEmpty()){
            throw new LeagueException("Mal equipo");
        }
        if (liga.get().isClosed()) {
            throw new LeagueException("La liga es privada");
        }
        if (liga.get().getTeams().size() >= 20){throw new IllegalArgumentException("Max of 20 teams allowed");}

        LeagueTeam leagueTeam = new LeagueTeam();
        leagueTeam.setTeam(teamMapper.toEntity(teamUpdateDTO));
        leagueTeam.setLeague(liga.get());

        liga.get().setOneTeam(leagueTeam);
        return leagueMapper.toDTO(leagueRepository.save(liga.get()));
    }

}
