package com.JPAVideoGames.TiliManager.service;

import com.JPAVideoGames.TiliManager.dto.leagueteamdto.LeagueTeamDTO;
import com.JPAVideoGames.TiliManager.dto.usertilidto.UserTiliDTO;
import com.JPAVideoGames.TiliManager.dto.usertilidto.UserTiliPassDTO;
import com.JPAVideoGames.TiliManager.exceptions.UserTiliException;
import com.JPAVideoGames.TiliManager.model.LeagueTeam;
import com.JPAVideoGames.TiliManager.repository.LeagueTeamRepository;
import com.JPAVideoGames.TiliManager.util.LeagueTeamMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    private final LeagueTeamRepository leagueTeamRepository;

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

    public Optional<LeagueTeam> getById(Long id) {
        return leagueTeamRepository.findById(id);
    }

    public Optional<LeagueTeamDTO> getByIdDTO(Long id) {
        return leagueTeamRepository.findById(id).map(leagueTeamMapper::toDTO);
    }

    public void delete(Long id) {
         leagueTeamRepository.deleteById(id);
    }


}
