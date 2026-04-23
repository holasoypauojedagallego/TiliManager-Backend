package com.JPAVideoGames.TiliManager.util;

import com.JPAVideoGames.TiliManager.model.Player;
import com.JPAVideoGames.TiliManager.model.Team;
import com.JPAVideoGames.TiliManager.model.UserTili;
import com.JPAVideoGames.TiliManager.repository.PlayerRepository;
import com.JPAVideoGames.TiliManager.repository.TeamRepository;
import com.JPAVideoGames.TiliManager.repository.UserTiliRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitialitzer implements CommandLineRunner {

    @Autowired
    private UserTiliRepository userTiliRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception{
        if (teamRepository.count() == 0){

            UserTili userTili = new UserTili();
            userTili.setName("BOT1-YALEL");
            userTili.setEmail("pojeda@elpuig.xeill.net");
            userTili.setPassword(passwordEncoder.encode("676767"));

            UserTili savedUserTili = userTiliRepository.save(userTili);

            List<Player> jugadores3 = new ArrayList<>(List.of(
                    new Player("Chuampi", 84, 84, 84),
                    new Player("Umtiti", 79, 76, 82),
                    new Player("Yamcha", 73, 75, 71),
                    new Player("Andrew Tate", 77, 87, 67),
                    new Player("Pepe Viyuela", 82, 83, 81),
                    new Player("Tadi Sambaudelio", 76, 80, 72),
                    new Player("Naranjito", 75, 76, 74)));

            Team teamFromUserTili = new Team();
            teamFromUserTili.setName("SKATERS KFC");
            teamFromUserTili.setMoney(0L);
            teamFromUserTili.setOwner(savedUserTili);
            teamRepository.save(teamFromUserTili);

            for (Player p : jugadores3){
                p.setTeamId(teamFromUserTili.getId());
                playerRepository.save(p);
            }

            teamFromUserTili.setPlayers(jugadores3);
            teamRepository.save(teamFromUserTili);
        }
    }
}
