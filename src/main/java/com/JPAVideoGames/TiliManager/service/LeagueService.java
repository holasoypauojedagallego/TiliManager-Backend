package com.JPAVideoGames.TiliManager.service;

import com.JPAVideoGames.TiliManager.dto.LeagueCreateDTO;
import com.JPAVideoGames.TiliManager.dto.LeagueDTO;
import com.JPAVideoGames.TiliManager.model.League;
import com.JPAVideoGames.TiliManager.model.UserTili;
import com.JPAVideoGames.TiliManager.repository.LeagueRepository;
import com.JPAVideoGames.TiliManager.util.LeagueMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Lazy
@Transactional
public class LeagueService {

    @Autowired
    @Lazy
    private LeagueMapper leagueMapper;

    private final LeagueRepository leagueRepository;

    public LeagueService(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    public List<LeagueDTO> getAll(){
        return leagueMapper.toDTO(leagueRepository.findAll());
    }

    public LeagueDTO createLeague(LeagueCreateDTO leagueCreateDTO){
        return leagueMapper.toDTO(leagueRepository.save(leagueMapper.toCreateEntity(leagueCreateDTO)));
    }

}
