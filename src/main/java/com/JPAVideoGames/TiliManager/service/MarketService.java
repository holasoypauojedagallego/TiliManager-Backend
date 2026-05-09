package com.JPAVideoGames.TiliManager.service;

import com.JPAVideoGames.TiliManager.dto.leaguedto.LeagueDTO;
import com.JPAVideoGames.TiliManager.dto.marketdto.MercadoDTO;
import com.JPAVideoGames.TiliManager.dto.playerleague.PlayerLeagueDTO;
import com.JPAVideoGames.TiliManager.model.PlayerLeague;
import com.JPAVideoGames.TiliManager.util.PlayerLeagueMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@EnableScheduling
public class MarketService {

    @Autowired
    private PlayerLeagueService playerLeagueService;

    @Autowired
    private LeagueService leagueService;

    private final List<MercadoDTO> mercados = new ArrayList<>();

    private List<LeagueDTO> leagues;

    @PostConstruct
    public void init() {
        setLeagues();
        for (int i = 0; i < getLeagues().size(); i++) {
            this.mercados.add(new MercadoDTO(getLeagues().get(i).getId(), mercadoJugadores(getLeagues().get(i).getId()), true));
        }
    }

    @Scheduled(cron = "00 59 23 * * * ")
    public void scheduled() {
        setLeagues();
        for (int i = 0; i < getLeagues().size(); i++) {
            this.mercados.get(i).setPlayers(mercadoJugadores(getLeagues().get(i).getId()));
            this.mercados.get(i).setFichable(false);
        }
    }

    public void anadir(long id) {
        setLeagues();
        for (int i = 0; i < getLeagues().size(); i++) {
            if (getLeagues().get(i).getId() == id) {
                this.mercados.add(new MercadoDTO(getLeagues().get(i).getId(), mercadoJugadores(getLeagues().get(i).getId()), true));
                break;
            }
        }
    }

    @Scheduled(cron = "00 00 16 * * * ")
    public void scheduledFichable() {
        if (this.mercados.isEmpty()) {return;}
        for (MercadoDTO mercado : this.mercados) {
            mercado.setFichable(true);
        }
    }

    public List<PlayerLeagueDTO> mercadoJugadores(long leagueId) {
        List<PlayerLeagueDTO> jugadoresAnte = playerLeagueService.getJugadoresByLeagueAndTeamIdNull(leagueId);
        if (jugadoresAnte.size() <= 20) {
            return jugadoresAnte;
        }
        Collections.shuffle(jugadoresAnte);
        return jugadoresAnte.subList(0, 20);
    }

    public void actualizarMercado(PlayerLeagueDTO p){
        for (MercadoDTO mercado : this.mercados) {
            List<PlayerLeagueDTO> playerLeagues = mercado.getPlayers();
            for (int i = 0; i < playerLeagues.size(); i++) {
                if (playerLeagues.get(i).getId() == p.getId() && playerLeagues.get(i).getLeague().getId() == p.getLeague().getId()) {
                    playerLeagues.set(i, p);
                }
            }
        }
    }

    public List<MercadoDTO> getMercados() {
        return mercados;
    }

    public Optional<MercadoDTO> getMercado(long id) {
        return getMercados().stream().filter(mercad -> mercad.getId() == id).findFirst();
    }

    public List<LeagueDTO> getLeagues() {
        return leagues;
    }

    public void setLeagues() {
        this.leagues = leagueService.getAll();
    }
}
