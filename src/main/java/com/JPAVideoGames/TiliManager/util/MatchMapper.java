package com.JPAVideoGames.TiliManager.util;

import com.JPAVideoGames.TiliManager.dto.MatchDTO;
import com.JPAVideoGames.TiliManager.model.Match;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MatchMapper {
    List<MatchDTO> toDTO(List<Match> matches);
    MatchDTO toDTO(Match match);
}
