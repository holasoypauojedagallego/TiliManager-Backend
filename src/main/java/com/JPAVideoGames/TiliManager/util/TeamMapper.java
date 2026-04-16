package com.JPAVideoGames.TiliManager.util;

import com.JPAVideoGames.TiliManager.dto.TeamDTO;
import com.JPAVideoGames.TiliManager.model.Team;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeamMapper {
    TeamDTO toDto(Team team);
    Team toEntity(TeamDTO teamDTO);
    List<TeamDTO> toDto(List<Team> teams);
}
