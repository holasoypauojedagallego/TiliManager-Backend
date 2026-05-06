package com.JPAVideoGames.TiliManager.util;

import com.JPAVideoGames.TiliManager.dto.leagueteamdto.LeagueTeamDTO;
import com.JPAVideoGames.TiliManager.model.LeagueTeam;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LeagueTeamMapper {
    LeagueTeamDTO toDTO(LeagueTeam leagueTeam);
    List<LeagueTeamDTO> toDTO(List<LeagueTeam> leagueTeam);
}
