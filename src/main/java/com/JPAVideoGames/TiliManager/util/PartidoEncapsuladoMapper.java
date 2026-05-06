package com.JPAVideoGames.TiliManager.util;

import com.JPAVideoGames.TiliManager.dto.matchdto.PartidoEncapsuladoDTO;
import com.JPAVideoGames.TiliManager.model.PartidoEncapsulado;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PartidoEncapsuladoMapper {
    PartidoEncapsuladoDTO toDTO(PartidoEncapsulado  partidoEncapsulado);
    List<PartidoEncapsuladoDTO> toDTO(List<PartidoEncapsulado>  partidoEncapsulado);
    PartidoEncapsulado toEntity(PartidoEncapsuladoDTO dto);
}
