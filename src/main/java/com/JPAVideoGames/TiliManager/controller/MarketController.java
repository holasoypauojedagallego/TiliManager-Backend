package com.JPAVideoGames.TiliManager.controller;

import com.JPAVideoGames.TiliManager.dto.marketdto.MercadoDTO;
import com.JPAVideoGames.TiliManager.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mercado")
public class MarketController {

    @Autowired
    private MarketService marketService;

    @GetMapping
    public ResponseEntity<List<MercadoDTO>> getMarkets() {
        return ResponseEntity.ok(marketService.getMercados());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MercadoDTO> getMarket(@PathVariable long id) {
        return marketService.getMercado(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

}
