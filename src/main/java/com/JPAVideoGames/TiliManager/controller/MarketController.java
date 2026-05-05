package com.JPAVideoGames.TiliManager.controller;

import com.JPAVideoGames.TiliManager.dto.marketdto.MercadoDTO;
import com.JPAVideoGames.TiliManager.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mercado")
public class MarketController {

    @Autowired
    private MarketService marketService;

    @GetMapping
    public ResponseEntity<MercadoDTO> getMarket() {
        return ResponseEntity.ok(marketService.getMercadoDTO());
    }

}
