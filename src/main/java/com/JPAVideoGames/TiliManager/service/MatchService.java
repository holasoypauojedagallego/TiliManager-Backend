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

    public void borrarLigaPartdios(Long id) {
        List<Match> matches = matchRepository.findAllByLeagueId(id);
        for (Match match : matches) {
            match.setLeague(null);
        }
        matchRepository.saveAll(matches);
    }

    public List<PartidoEncapsuladoDTO> empezarCodigo(TeamUpdateDTO localteamDTO) throws TeamException {
        return partidoEncapsuladoMapper.toDTO(codigo(teamMapper.toEntity(localteamDTO), teamMapper.toEntity(teamService.searchRivalTeam(localteamDTO.getId())), Optional.empty()).getPartidoEncapsulado());
    }

    public List<PartidoEncapsuladoDTO> torneoSimuladoP1(TeamUpdateDTO localteamDTO) {
        // TeamDTO visitorTeam = teamService.getTeamByName("SKATERS KFC").orElseThrow(() -> new RuntimeException("Equipo no encontrado")); Para concretamente el Yali
        List<TeamDTO> equipos = teamService.getTeamByRole(UserTiliRole.BOT);
        TeamDTO visitorTeam = equipos.get((int) (Math.random() * equipos.size()));

        Match partido = codigo(teamMapper.toEntity(localteamDTO), teamMapper.toEntity(visitorTeam), Optional.empty());

        teamService.dineroPorResultado(localteamDTO, (partido.getLocalTeamGoals() - partido.getVisitorTeamGoals()));
        return partidoEncapsuladoMapper.toDTO(partido.getPartidoEncapsulado());
    }

    public Match codigo(Team localTeam, Team visitorTeam, Optional<League> league) {
        int localTeamRating = localTeam.getPlayers().stream().mapToInt(p -> p.getPlayer().getRating()).sum() / (localTeam.getPlayers().size() - 2);
        int visitorTeamRating = visitorTeam.getPlayers().stream().mapToInt(p -> p.getPlayer().getRating()).sum() / (visitorTeam.getPlayers().size() - 2);

        int localAttackRating = localTeam.getPlayers().stream().mapToInt(p -> p.getPlayer().getAttack()).sum();
        int localDefenseRating = localTeam.getPlayers().stream().mapToInt(p -> p.getPlayer().getDefense()).sum();
        int localGoals = 0;

        int visitorAttackRating = visitorTeam.getPlayers().stream().mapToInt(p -> p.getPlayer().getAttack()).sum();
        int visitorDefenseRating = visitorTeam.getPlayers().stream().mapToInt(p -> p.getPlayer().getDefense()).sum();
        int visitorGoals = 0;

        int porcentajeGanaLocal = 30 + (localTeamRating - visitorTeamRating);
        int porcentajeGanaVisitante = 30 + (visitorTeamRating - localTeamRating);
        if (porcentajeGanaLocal <= 0){ porcentajeGanaLocal = 1; porcentajeGanaVisitante = 59; }
        if (porcentajeGanaVisitante <= 0){ porcentajeGanaVisitante = 1; porcentajeGanaLocal = 59; }

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
                PlayerLeague jugador = localTeam.getPlayers().get((int)(Math.random() * localTeam.getPlayers().size()));
                partidoalgo.setJugador(jugador.getPlayer());
                partidoalgo.setLocal(true);

                int golito = 0;

                if (queSucede < 0.33) {
                    golito = gol( localAttackRating, visitorDefenseRating);
                    localGoals = localGoals + golito;
                }

                partidoalgo.setSucede(localGoals);
                partidoalgo.setGolesLocal(localGoals);
                if (league.isPresent()) jugador.setGoles(jugador.getGoles() + golito);
                System.out.println("Local: " + localGoals);
            } else  {
                porcentajeGanaVisitante--;
                partidoalgo.setEquipo(visitorTeam);
                PlayerLeague jugador = visitorTeam.getPlayers().get((int)(Math.random() * visitorTeam.getPlayers().size()));
                partidoalgo.setJugador(jugador.getPlayer());

                partidoalgo.setLocal(false);

                int golito = 0;

                if (queSucede < 0.33) {
                    golito = gol(visitorAttackRating, localDefenseRating);
                    visitorGoals += golito;
                }

                partidoalgo.setSucede(visitorGoals);
                partidoalgo.setGolesVisitante(visitorGoals);
                if (league.isPresent()) jugador.setGoles(jugador.getGoles() + golito);
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
        league.ifPresent(match::setLeague);
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