package com.JPAVideoGames.TiliManager.service;

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
            new Player(2, "Adrian", 87, 87, 87),
            new Player(3, "Justin", 87, 83, 92),
            new Player(4, "Tadi", 87, 83, 92),
            new Player(5, "Tadi", 87, 86, 89),
            new Player(6, "Tadi", 87, 84, 91),
            new Player(7, "fa", 87, 85, 90)
    ));

    private static final List<Player> jugadores2 = new ArrayList<>(List.of(
            new Player(11, "cvA", 87, 81, 75),
            new Player(21, "Adrian", 87, 83, 92),
            new Player(31, "Justin", 87, 95, 78),
            new Player(41, "Tadi", 87, 83, 92),
            new Player(51, "ras", 87, 83, 92),
            new Player(61, "AS", 87, 80, 94),
            new Player(71, "Tadi", 87, 97, 79)));


    private static final List<Team> teams = new ArrayList<>(List.of(
            new Team(1, "Pau", "Equipo", jugadores),
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

    public void codigo() {
        int localTeamRating = 184;
        int localAttackRating = jugadores.stream().mapToInt(Player::getAttack).sum();
        int localDefenseRating = jugadores.stream().mapToInt(Player::getDefense).sum();
        int localGoals = 0;
        int visitorTeamRating = 182;
        int visitorAttackRating = jugadores2.stream().mapToInt(Player::getAttack).sum();
        int visitorDefenseRating = jugadores2.stream().mapToInt(Player::getDefense).sum();
        int visitorGoals = 0;

        int pasaalgo = 0;
        int noPasaalgo = 0;

        int porcentajeGanaLocal = 45 + (localTeamRating - visitorTeamRating);
        int porcentajeGanaVisitante = 45 + (visitorTeamRating - localTeamRating);
        if (porcentajeGanaLocal <= 0){ porcentajeGanaLocal = 1; porcentajeGanaVisitante = 89; }
        if (porcentajeGanaVisitante <= 0){ porcentajeGanaVisitante = 1; porcentajeGanaLocal = 89; }

        int contador = 0;

        while (porcentajeGanaLocal > 0  || porcentajeGanaVisitante > 0) {
            contador++;

            System.out.println("Minuto: " + contador);

            boolean sucedealgo = Math.random() < 0.33;
            if (!sucedealgo) {
                System.out.println("No pasa nada");
                noPasaalgo++;
                if (porcentajeGanaLocal >= porcentajeGanaVisitante) porcentajeGanaLocal--;
                else porcentajeGanaVisitante--;
                continue;
            }

            System.out.println("Pasa algo");
            pasaalgo++;
            float queSucede = (float) Math.random();

            if (porcentajeGanaLocal >= porcentajeGanaVisitante) {
                porcentajeGanaLocal--;

                if (queSucede < 0.3) {
                    localGoals = localGoals + gol( localAttackRating, visitorDefenseRating);
                }

                System.out.println("Local: " + localGoals);
            } else  {
                porcentajeGanaVisitante--;

                if (queSucede < 0.3) {
                    visitorGoals += gol(visitorAttackRating, localDefenseRating);
                }

                System.out.println("Visitante: " + visitorGoals);
            }

        }

        System.out.println("Pasa Algo " + pasaalgo);
        System.out.println("NO Pasa Algo " + noPasaalgo);

        System.out.println(porcentajeGanaLocal + "; Goles Local: " + localGoals);
        System.out.println(porcentajeGanaVisitante + "; Goles Visitante: " + visitorGoals);
    }

    public int gol(int attackTeamRating, int defenseTeamRating) {

        float probabilidad = (float) Math.random();
        float diferencia = 0.01f * (attackTeamRating - defenseTeamRating);
        float probabilidadFinal = 0.52f - diferencia;

        return (probabilidad > probabilidadFinal) ? 1 : 0;
    }

}
