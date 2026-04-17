package com.JPAVideoGames.TiliManager.util;

import com.JPAVideoGames.TiliManager.dto.UserTiliCreateDTO;
import com.JPAVideoGames.TiliManager.dto.UserTiliDTO;
import com.JPAVideoGames.TiliManager.dto.UserTiliLoginDTO;
import com.JPAVideoGames.TiliManager.dto.UserTiliPassDTO;
import com.JPAVideoGames.TiliManager.model.UserTili;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserTiliMapper {
    UserTiliDTO toDto(UserTili user);
    UserTili toEntity(UserTiliDTO dto);
    UserTiliCreateDTO toCreateDTO(UserTili user);
    UserTili toCreateEntity(UserTiliCreateDTO dto);
    List<UserTiliDTO> toDto(List<UserTili> userTiliList);
    UserTiliPassDTO toPassDto(UserTili user);
    UserTili toEntity(UserTiliPassDTO dto);
    UserTiliLoginDTO toLoginDto(UserTiliPassDTO userTiliLoginDTO);
}
