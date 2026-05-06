package com.JPAVideoGames.TiliManager.service;

import com.JPAVideoGames.TiliManager.dto.matchdto.MatchDTO;
import com.JPAVideoGames.TiliManager.dto.matchdto.PartidoEncapsuladoDTO;
import com.JPAVideoGames.TiliManager.model.*;
import com.JPAVideoGames.TiliManager.model.PartidoEncapsulado;
import com.JPAVideoGames.TiliManager.dto.teamdto.TeamDTO;
import com.JPAVideoGames.TiliManager.dto.teamdto.TeamUpdateDTO;
import com.JPAVideoGames.TiliManager.exceptions.TeamException;
import com.JPAVideoGames.TiliManager.repository.MatchRepository;
import com.JPAVideoGames.TiliManager.util.MatchMapper;
import com.JPAVideoGames.TiliManager.util.PartidoEncapsuladoMapper;
import com.JPAVideoGames.TiliManager.util.TeamMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Lazy
@Transactional
public class MatchService {

    @Autowired
    @Lazy
    private TeamMapper teamMapper;

    @Autowired
    @Lazy
    private TeamService teamService;

    @Autowired
    @Lazy
    private MatchMapper matchMapper;

    @Autowired
    @Lazy
    private PartidoEncapsuladoMapper partidoEncapsuladoMapper;

    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public List<MatchDTO> getPartidos() {
        return matchMapper.toDTO(matchRepository.findAll());
    }

    public Optional<MatchDTO> getPartidoById(long id) {
        return matchRepository.findById(id).map(matchMapper::toDTO);
    }

    public List<PartidoEncapsuladoDTO> empezarCodigo(TeamUpdateDTO localteamDTO) throws TeamException {
        return partidoEncapsuladoMapper.toDTO(codigo(teamMapper.toEntity(localteamDTO), teamMapper.toEntity(teamService.searchRivalTeam(localteamDTO.getId()))).getPartidoEncapsulado());
    }

    public List<PartidoEncapsuladoDTO> torneoSimuladoP1(TeamUpdateDTO localteamDTO) {
        // TeamDTO visitorTeam = teamService.getTeamByName("SKATERS KFC").orElseThrow(() -> new RuntimeException("Equipo no encontrado")); Para concretamente el Yali
        List<TeamDTO> equipos = teamService.getTeamByRole(UserTiliRole.BOT);
        TeamDTO visitorTeam = equipos.get((int) (Math.random() * equipos.size()));

        Match partido = codigo(teamMapper.toEntity(localteamDTO), teamMapper.toEntity(visitorTeam));

        teamService.dineroPorResultado(localteamDTO, (partido.getLocalTeamGoals() - partido.getVisitorTeamGoals()));
        return partidoEncapsuladoMapper.toDTO(partido.getPartidoEncapsulado());
    }

    public Match codigo(Team localTeam, Team visitorTeam) {
        int localTeamRating = localTeam.getPlayers().stream().mapToInt(Player::getRating).sum() / (localTeam.getPlayers().size() - 2);
        int visitorTeamRating = visitorTeam.getPlayers().stream().mapToInt(Player::getRating).sum() / (visitorTeam.getPlayers().size() - 2);

        int localAttackRating = localTeam.getPlayers().stream().mapToInt(Player::getAttack).sum();
        int localDefenseRating = localTeam.getPlayers().stream().mapToInt(Player::getDefense).sum();
        int localGoals = 0;

        int visitorAttackRating = visitorTeam.getPlayers().stream().mapToInt(Player::getAttack).sum();
        int visitorDefenseRating = visitorTeam.getPlayers().stream().mapToInt(Player::getDefense).sum();
        int visitorGoals = 0;

        int porcentajeGanaLocal = 45 + (localTeamRating - visitorTeamRating);
        int porcentajeGanaVisitante = 45 + (visitorTeamRating - localTeamRating);
        if (porcentajeGanaLocal <= 0){ porcentajeGanaLocal = 1; porcentajeGanaVisitante = 89; }
        if (porcentajeGanaVisitante <= 0){ porcentajeGanaVisitante = 1; porcentajeGanaLocal = 89; }

        int contador = 0;
        List<PartidoEncapsulado> partidoEncapsulados = new ArrayList<>();

        while (porcentajeGanaLocal > 0  || porcentajeGanaVisitante > 0) {
            contador++;
            PartidoEncapsulado partidoalgo = new PartidoEncapsulado(contador);

            System.out.println("Minuto: " + contador);

            boolean sucedealgo = Math.random() < 0.33;
            if (!sucedealgo) {
                System.out.println("No pasa nada");
                if (porcentajeGanaLocal >= porcentajeGanaVisitante) porcentajeGanaLocal--;
                else porcentajeGanaVisitante--;
                continue;
            }

            System.out.println("Pasa algo");
            float queSucede = (float) Math.random();

            if (porcentajeGanaLocal >= porcentajeGanaVisitante) {
                porcentajeGanaLocal--;
                partidoalgo.setEquipo(localTeam);
                partidoalgo.setJugador(localTeam.getPlayers().get((int)(Math.random() * localTeam.getPlayers().size())));
                partidoalgo.setLocal(true);

                if (queSucede < 0.3) {
                    localGoals = localGoals + gol( localAttackRating, visitorDefenseRating);
                }

                partidoalgo.setSucede(localGoals);
                System.out.println("Local: " + localGoals);
            } else  {
                porcentajeGanaVisitante--;
                partidoalgo.setEquipo(visitorTeam);
                partidoalgo.setJugador(visitorTeam.getPlayers().get((int)(Math.random() * visitorTeam.getPlayers().size())));

                partidoalgo.setLocal(false);

                if (queSucede < 0.3) {
                    visitorGoals += gol(visitorAttackRating, localDefenseRating);
                }

                partidoalgo.setSucede(visitorGoals);
                System.out.println("Visitante: " + visitorGoals);
            }
            if (!partidoalgo.getEquipo().getName().isEmpty() && partidoalgo.getJugador() != null){
                partidoEncapsulados.add(partidoalgo);
            }
        }

        System.out.println(porcentajeGanaLocal + "; Goles Local: " + localGoals);
        System.out.println(porcentajeGanaVisitante + "; Goles Visitante: " + visitorGoals);

        Match match = new Match();
        match.setPartidoEncapsulado(partidoEncapsulados);
        match.setLocalTeam(localTeam);
        match.setVisitorTeam(visitorTeam);
        match.setLocalTeamGoals(localGoals);
        match.setVisitorTeamGoals(visitorGoals);
        matchRepository.save(match);

        return match;
    }

    public int gol(int attackTeamRating, int defenseTeamRating) {

        float probabilidad = (float) Math.random();
        float diferencia = 0.01f * (attackTeamRating - defenseTeamRating);
        float probabilidadFinal = 0.52f - diferencia;

        return (probabilidad > probabilidadFinal) ? 1 : 0;
    }

}