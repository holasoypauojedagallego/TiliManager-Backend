package com.JPAVideoGames.TiliManager.util;


import com.JPAVideoGames.TiliManager.dto.leaguedto.LeagueCreateDTO;
import com.JPAVideoGames.TiliManager.dto.leaguedto.LeagueDTO;
import com.JPAVideoGames.TiliManager.dto.leaguedto.LeagueDeleteDTO;
import com.JPAVideoGames.TiliManager.dto.leagueteamdto.LeagueTeamDTO;
import com.JPAVideoGames.TiliManager.model.League;
import com.JPAVideoGames.TiliManager.model.LeagueTeam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LeagueMapper {
    League toCreateEntity(LeagueCreateDTO leagueCreateDTO);
    League toDeleteEntity(LeagueDeleteDTO leagueDeleteDTO);
    LeagueDTO toDTO(League league);
    List<LeagueDTO> toDTO(List<League> leagues);

    @Mapping(target = "league", source = "league.id")
    LeagueTeamDTO toLeagueTeamDTO(LeagueTeam leagueTeam);
}
