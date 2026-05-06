package com.JPAVideoGames.TiliManager.service;

import com.JPAVideoGames.TiliManager.dto.leagueteamdto.LeagueTeamDTO;
import com.JPAVideoGames.TiliManager.repository.LeagueTeamRepository;
import com.JPAVideoGames.TiliManager.util.LeagueTeamMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Lazy
@Transactional
public class LeagueTeamService {

    @Autowired
    @Lazy
    private LeagueTeamMapper leagueTeamMapper;

    public final LeagueTeamRepository leagueTeamRepository;

    public LeagueTeamService(LeagueTeamRepository leagueTeamRepository) {
        this.leagueTeamRepository = leagueTeamRepository;
    }

    public List<LeagueTeamDTO> getAll() {
        return  leagueTeamMapper.toDTO(leagueTeamRepository.findAll());
    }

}
