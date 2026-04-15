package com.JPAVideoGames.TiliManager.service;

import com.JPAVideoGames.TiliManager.dto.PartidoEncapsuladoDTO;
import com.JPAVideoGames.TiliManager.model.Player;
import com.JPAVideoGames.TiliManager.model.Team;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Lazy
public class PlayerService {

    private static final List<Player> jugadores = new ArrayList<>(List.of(
            new Player(1, "Pau", 87, 83, 92),
            new Player(2, "Adriutusn", 87, 87, 87),
            new Player(3, "Gustin", 87, 83, 92),
            new Player(4, "Tadi", 87, 83, 92),
            new Player(5, "Safu", 87, 86, 89),
            new Player(6, "MrGay", 87, 84, 91),
            new Player(7, "Arthur Morgan", 87, 85, 90)
    ));

    private static final List<Player> jugadores2 = new ArrayList<>(List.of(
            new Player(11, "CurriculumVitae", 87, 81, 75),
            new Player(21, "Adrian", 83, 83, 92),
            new Player(31, "Justin", 87, 95, 78),
            new Player(41, "TadiPro", 97, 83, 92),
            new Player(51, "John Marston", 82, 83, 92),
            new Player(61, "CuloGordo40", 85, 80, 94),
            new Player(71, "MrPopo", 83, 97, 79)));


    private static final List<Team> teams = new ArrayList<>(List.of(
            new Team(1, "Pau", "EquipoFinal", jugadores),
            new Team(2, "Adri", "Socialista", jugadores2)
    ));


    public List<Player> getJugadores() {
        return jugadores;
    }

    public List<Team> getEquipo() {
        return teams;
    }

    public Optional<Player> getJugador(long id) {
        return jugadores.stream().filter(player-> player.getId() == id).findFirst();
    }

    public Player postJugador(Player player) {
        jugadores.add(player);
        return player;
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