package com.JPAVideoGames.TiliManager.service;

import com.JPAVideoGames.TiliManager.dto.PartidoEncapsuladoDTO;
import com.JPAVideoGames.TiliManager.dto.TeamDTO;
import com.JPAVideoGames.TiliManager.model.Match;
import com.JPAVideoGames.TiliManager.model.Player;
import com.JPAVideoGames.TiliManager.model.Team;
import com.JPAVideoGames.TiliManager.repository.MatchRepository;
import com.JPAVideoGames.TiliManager.util.TeamMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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

    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    private static final List<Player> jugadores = new ArrayList<>(List.of(
            new Player("Pau", 87, 83, 92),
            new Player("Adriutusn", 87, 87, 87 ),
            new Player("Gustin", 87, 83, 92),
            new Player("Tadi", 87, 83, 92),
            new Player("Safu", 87, 86, 89),
            new Player("MrGay", 87, 84, 91),
            new Player("Arthur Morgan", 87, 85, 90)
    ));

    private static final List<Player> jugadores2 = new ArrayList<>(List.of(
            new Player("CurriculumVitae", 87, 81, 75),
            new Player("Adrian", 83, 83, 92),
            new Player("Justin", 87, 95, 78),
            new Player("TadiPro", 97, 83, 92),
            new Player("John Marston", 82, 83, 92),
            new Player("CuloGordo", 85, 80, 94),
            new Player("MrPopo", 83, 97, 79)));

    public List<Player> getJugadortt() {
        return jugadores;
    }

    public List<Player> getJugadorff() {
        return jugadores2;
    }

    public List<Match> getPartidos() {
        return matchRepository.findAll();
    }

    public List<PartidoEncapsuladoDTO> empezarCodigo(TeamDTO localteamDTO, TeamDTO visitorTeamDTO) {
        List<PartidoEncapsuladoDTO> partidoEncapsulados = codigo(teamMapper.toEntity(localteamDTO), teamMapper.toEntity(visitorTeamDTO));

        Match match = new Match();
        match.setPartidoEncapsulado(partidoEncapsulados);
        match.setLocalTeam(teamMapper.toEntity(localteamDTO));
        match.setVisitorTeam(teamMapper.toEntity(visitorTeamDTO));

        matchRepository.save(match);
        return partidoEncapsulados;
    }

    public List<PartidoEncapsuladoDTO> torneoSimuladoP1(TeamDTO localteamDTO) {
        TeamDTO visitorTeam = teamService.getTeamByName("SKATERS KFC").orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        List<PartidoEncapsuladoDTO> partidoEncapsulados = codigo(teamMapper.toEntity(localteamDTO), teamMapper.toEntity(visitorTeam));

        Match match = new Match();
        match.setPartidoEncapsulado(partidoEncapsulados);
        match.setLocalTeam(teamMapper.toEntity(localteamDTO));
        match.setVisitorTeam(teamMapper.toEntity(visitorTeam));
        matchRepository.save(match);

        return partidoEncapsulados;
    }

    public List<PartidoEncapsuladoDTO> codigo(Team localTeam, Team visitorTeam) {
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
        List<PartidoEncapsuladoDTO> partidoEncapsulados = new ArrayList<>();

        while (porcentajeGanaLocal > 0  || porcentajeGanaVisitante > 0) {
            contador++;
            PartidoEncapsuladoDTO partidoalgo = new PartidoEncapsuladoDTO(contador);

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
                partidoalgo.setJugador(localTeam.getPlayers().get((int)(Math.random() * 7)));
                partidoalgo.setLocal(true);

                if (queSucede < 0.3) {
                    localGoals = localGoals + gol( localAttackRating, visitorDefenseRating);
                }

                partidoalgo.setSucede(localGoals);
                System.out.println("Local: " + localGoals);
            } else  {
                porcentajeGanaVisitante--;
                partidoalgo.setEquipo(visitorTeam);
                partidoalgo.setJugador(visitorTeam.getPlayers().get((int)(Math.random() * 7)));

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

        return partidoEncapsulados;
    }

    public int gol(int attackTeamRating, int defenseTeamRating) {

        float probabilidad = (float) Math.random();
        float diferencia = 0.01f * (attackTeamRating - defenseTeamRating);
        float probabilidadFinal = 0.52f - diferencia;

        return (probabilidad > probabilidadFinal) ? 1 : 0;
    }

}