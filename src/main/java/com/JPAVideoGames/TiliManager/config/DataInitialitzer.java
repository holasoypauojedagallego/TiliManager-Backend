package com.JPAVideoGames.TiliManager.config;

import com.JPAVideoGames.TiliManager.dto.leaguedto.LeagueCreateDTO;
import com.JPAVideoGames.TiliManager.model.*;
import com.JPAVideoGames.TiliManager.repository.PlayerRepository;
import com.JPAVideoGames.TiliManager.repository.TeamRepository;
import com.JPAVideoGames.TiliManager.repository.UserTiliRepository;
import com.JPAVideoGames.TiliManager.service.LeagueService;
import com.JPAVideoGames.TiliManager.util.UserTiliMapper;
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

            UserTili coach1 = new UserTili();
            coach1.setName("Harry Savage");
            coach1.setEmail("liga1@elpuig.xeill.net");
            coach1.setPassword(passwordEncoder.encode("676767"));
            coach1.setRole(UserTiliRole.BOT);
            UserTili savedcoach1 = userTiliRepository.save(coach1);

            UserTili coach2 = new UserTili();
            coach2.setName("Manny Artic");
            coach2.setEmail("liga2@elpuig.xeill.net");
            coach2.setPassword(passwordEncoder.encode("676767"));
            coach2.setRole(UserTiliRole.BOT);
            UserTili savedcoach2 = userTiliRepository.save(coach2);

            UserTili coach3 = new UserTili();
            coach3.setName("Hekyll Jyde");
            coach3.setEmail("liga3@elpuig.xeill.net");
            coach3.setPassword(passwordEncoder.encode("676767"));
            coach3.setRole(UserTiliRole.BOT);
            UserTili savedcoach3 = userTiliRepository.save(coach3);

            UserTili coach4 = new UserTili();
            coach4.setName("Newton Thomas");
            coach4.setEmail("liga4@elpuig.xeill.net");
            coach4.setPassword(passwordEncoder.encode("676767"));
            coach4.setRole(UserTiliRole.BOT);
            UserTili savedcoach4 = userTiliRepository.save(coach4);

            UserTili coach5 = new UserTili();
            coach5.setName("Sammy Igajima");
            coach5.setEmail("liga5@elpuig.xeill.net");
            coach5.setPassword(passwordEncoder.encode("676767"));
            coach5.setRole(UserTiliRole.BOT);
            UserTili savedcoach5 = userTiliRepository.save(coach5);

            UserTili coach6 = new UserTili();
            coach6.setName("Seth Nichols");
            coach6.setEmail("liga6@elpuig.xeill.net");
            coach6.setPassword(passwordEncoder.encode("676767"));
            coach6.setRole(UserTiliRole.BOT);
            UserTili savedcoach6 = userTiliRepository.save(coach6);

            UserTili coach7 = new UserTili();
            coach7.setName("Hilton Bernaton");
            coach7.setEmail("liga7@elpuig.xeill.net");
            coach7.setPassword(passwordEncoder.encode("676767"));
            coach7.setRole(UserTiliRole.BOT);
            UserTili savedcoach7 = userTiliRepository.save(coach7);

            UserTili coach8 = new UserTili();
            coach8.setName("Windell Balding");
            coach8.setEmail("liga8@elpuig.xeill.net");
            coach8.setPassword(passwordEncoder.encode("676767"));
            coach8.setRole(UserTiliRole.BOT);
            UserTili savedcoach8 = userTiliRepository.save(coach8);

            UserTili coach9 = new UserTili();
            coach9.setName("Isaias Setik");
            coach9.setEmail("liga9@elpuig.xeill.net");
            coach9.setPassword(passwordEncoder.encode("676767"));
            coach9.setRole(UserTiliRole.BOT);
            UserTili savedcoach9 = userTiliRepository.save(coach9);


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
