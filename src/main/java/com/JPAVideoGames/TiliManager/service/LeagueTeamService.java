package com.JPAVideoGames.TiliManager.service;

import com.JPAVideoGames.TiliManager.dto.leagueteamdto.LeagueTeamDTO;
import com.JPAVideoGames.TiliManager.dto.usertilidto.UserTiliDTO;
import com.JPAVideoGames.TiliManager.dto.usertilidto.UserTiliPassDTO;
import com.JPAVideoGames.TiliManager.exceptions.UserTiliException;
import com.JPAVideoGames.TiliManager.repository.LeagueTeamRepository;
import com.JPAVideoGames.TiliManager.util.LeagueTeamMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Lazy
@Transactional
public class LeagueTeamService {

    @Autowired
    @Lazy
    private LeagueTeamMapper leagueTeamMapper;

    @Autowired
    @Lazy
    private UserTiliService userTiliService;

    public final LeagueTeamRepository leagueTeamRepository;

    public LeagueTeamService(LeagueTeamRepository leagueTeamRepository) {
        this.leagueTeamRepository = leagueTeamRepository;
    }

    public List<LeagueTeamDTO> getAll() {
        return leagueTeamMapper.toDTO(leagueTeamRepository.findAll());
    }

    public List<LeagueTeamDTO> getAllByTeamOwnerId(UserTiliPassDTO userTiliPassDTO) throws UserTiliException {
        Optional<UserTiliDTO> userTiliDTO = userTiliService.getById(userTiliPassDTO.getId());
        if (userTiliDTO.isEmpty()) throw new UserTiliException("Este usuario no existe");
        return  leagueTeamMapper.toDTO(leagueTeamRepository.findAllByTeamOwnerId(userTiliPassDTO.getId()));
    }


}
