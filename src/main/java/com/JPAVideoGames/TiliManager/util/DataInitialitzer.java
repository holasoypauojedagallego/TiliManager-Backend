package com.JPAVideoGames.TiliManager.util;

import com.JPAVideoGames.TiliManager.dto.leaguedto.LeagueCreateDTO;
import com.JPAVideoGames.TiliManager.model.*;
import com.JPAVideoGames.TiliManager.repository.PlayerRepository;
import com.JPAVideoGames.TiliManager.repository.TeamRepository;
import com.JPAVideoGames.TiliManager.repository.UserTiliRepository;
import com.JPAVideoGames.TiliManager.service.LeagueService;
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
    private LeagueService leagueService;

    @Autowired
    private UserTiliMapper userTiliMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception{
        if (teamRepository.count() == 0){

            UserTili adminTili = new UserTili();
            adminTili.setName("admin");
            adminTili.setEmail("admin@elpuig.xeill.net");
            adminTili.setPassword(passwordEncoder.encode("676767"));
            adminTili.setRole(UserTiliRole.ADMIN);
            UserTili adminSaved = userTiliRepository.save(adminTili);

            LeagueCreateDTO leagueCreateDTO = new LeagueCreateDTO();
            leagueCreateDTO.setName("Liga Inicial");
            leagueCreateDTO.setOwner(userTiliMapper.toPassDto(adminSaved));
            leagueService.createLeague(leagueCreateDTO);

            UserTili userTili = new UserTili();
            userTili.setName("BOT1-YALEL");
            userTili.setEmail("pojeda@elpuig.xeill.net");
            userTili.setPassword(passwordEncoder.encode("676767"));
            userTili.setRole(UserTiliRole.BOT);
            UserTili savedUserTili = userTiliRepository.save(userTili);

            List<Player> jugadores3 = new ArrayList<>(List.of(
                    new Player("Chuampi", 84, 84, 84, true),
                    new Player("Umtiti", 79, 76, 82, true),
                    new Player("Yamcha", 73, 75, 71, true),
                    new Player("Andrew Tate", 77, 87, 67, true),
                    new Player("Pepe Viyuela", 82, 83, 81, true),
                    new Player("Tadi Sambaudelio", 76, 80, 72, true),
                    new Player("Naranjito", 75, 76, 74, true)));

            Team teamFromUserTili = new Team();
            teamFromUserTili.setName("SKATERS KFC");
            teamFromUserTili.setMoney(0L);
            teamFromUserTili.setOwner(savedUserTili);
            teamRepository.save(teamFromUserTili);

            jugadores3.forEach(player -> playerRepository.save(player));
            List<PlayerLeague> players3 = jugadores3.stream().map(PlayerLeague::new).toList();

            teamFromUserTili.setPlayers(players3);
            teamRepository.save(teamFromUserTili);
        }
    }
}
