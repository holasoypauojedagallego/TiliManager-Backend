package com.JPAVideoGames.TiliManager.util;


import com.JPAVideoGames.TiliManager.dto.playerleague.PlayerLeagueDTO;
import com.JPAVideoGames.TiliManager.model.PlayerLeague;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlayerLeagueMapper {
    List<PlayerLeagueDTO> toDTO(List<PlayerLeague> playerLeague);
    PlayerLeagueDTO toDTO(PlayerLeague playerLeague);
}
