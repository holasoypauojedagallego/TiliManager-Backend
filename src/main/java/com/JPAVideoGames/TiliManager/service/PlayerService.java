package com.JPAVideoGames.TiliManager.service;

import com.JPAVideoGames.TiliManager.model.Player;
import com.JPAVideoGames.TiliManager.model.Team;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Lazy
public class PlayerService {

    private static final List<Player> jugadores = new ArrayList<>(List.of(
            new Player(1, "Pau ", 87, 83, 92),
            new Player(2, "Adrian ", 87, 87, 87),
            new Player(3, "Justin ", 87, 83, 92),
            new Player(4, "Tadi", 87, 83, 92),
            new Player(5, "Tadi", 87, 86, 89),
            new Player(6, "Tadi", 87, 84, 91),
            new Player(7, "fa", 87, 85, 90)
    ));

    private static final List<Player> jugadorTeams = new ArrayList<>(List.of(
            new Player(11, "cvA ", 87, 81, 75),
            new Player(21, "Adrian ", 87, 83, 92),
            new Player(31, "Justin ", 87, 95, 78),
            new Player(41, "Tadi", 87, 83, 92),
            new Player(51, "ras", 87, 83, 92),
            new Player(61, "AS", 87, 80, 94),
            new Player(71, "Tadi", 87, 97, 79)));


    private static final List<Team> teams = new ArrayList<>(List.of(
            new Team(1, "Pau", "Equipo", jugadores),
            new Team(2, "Adri", "Socialista", jugadorTeams)
    ));


    public List<Player> getJugadores() {
        return jugadores;
    }

    public List<Team> getEquipo() {
        return teams;
    }

    public Player getJugador(long id) {
        return jugadores.stream().filter(player-> player.getId() == id).findFirst().orElse(null);
    }

    public Player postJugador(Player player) {
        jugadores.add(player);
        return player;
    }

    public void logica() {
        int localTeam = 0;
        int localRatingTeam = 172;
        int visitorTeam= 0;
        int visitorRatingTeam= 188;
        boolean momentoPartido;
        int ganaLocal = 0;
        for (int i = 0; i < 91; i++) {
            System.out.println("Minuto: " + i);
            double rating = (double) ((localRatingTeam + ganaLocal) - visitorRatingTeam) / 33;
            System.out.println(rating);
            boolean momentoPartido1 = Math.random() > (0.5 - rating);
            if (momentoPartido1) {
                localTeam++;
                ganaLocal--;
            } else {
                visitorTeam++;
            }
            System.out.println("Gana local: " + momentoPartido1);
        }
        System.out.println("\nLocal: " + localTeam);
        System.out.println("Visitor: " + visitorTeam);
    }

    public void codigo() {
        int localTeamRating = 185;
        int localGoals = 0;
        int visitorTeamRating = 194;
        int visitorGoals = 0;

        int maximaValoracionGrupal = localTeamRating + visitorTeamRating;
        double porcentajeGanaLocal = (double) localTeamRating / maximaValoracionGrupal * 100;
        double porcentajeGanaVisitante = (double) visitorTeamRating / maximaValoracionGrupal * 100;

        System.out.println(porcentajeGanaLocal + " " + localGoals);
        System.out.println(porcentajeGanaVisitante + " " + visitorGoals);
    }

    public boolean gol(int attackTeamRating, int defenseTeamRating) {
        System.out.println(attackTeamRating);
        System.out.println(defenseTeamRating);
        return true;
    }

}
