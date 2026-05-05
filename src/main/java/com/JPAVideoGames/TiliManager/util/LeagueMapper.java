package com.JPAVideoGames.TiliManager.util;

import com.JPAVideoGames.TiliManager.dto.LeagueCreateDTO;
import com.JPAVideoGames.TiliManager.dto.LeagueDTO;
import com.JPAVideoGames.TiliManager.model.League;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LeagueMapper {
    League toCreateEntity(LeagueCreateDTO leagueCreateDTO);
    LeagueDTO toDTO(League league);
    List<LeagueDTO> toDTO(List<League> leagues);
}
