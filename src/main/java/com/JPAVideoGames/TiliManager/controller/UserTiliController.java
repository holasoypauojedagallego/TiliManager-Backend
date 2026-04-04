package com.JPAVideoGames.TiliManager.controller;

import com.JPAVideoGames.TiliManager.dto.UserTiliCreateDTO;
import com.JPAVideoGames.TiliManager.dto.UserTiliDTO;
import com.JPAVideoGames.TiliManager.service.UserTiliService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserTiliController {

    @Autowired
    private UserTiliService userTiliService;

    @GetMapping
    public ResponseEntity<List<UserTiliDTO>> getUserTili() {
        return ResponseEntity.ok(userTiliService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserTiliDTO> getUserTiliById(@PathVariable long id){
        return userTiliService.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserTiliDTO> getUserTiliByEmail(@PathVariable String email){
        return userTiliService.getByEmail(email).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<UserTiliDTO> postUserTili(@RequestBody UserTiliCreateDTO userTili) {
        return ResponseEntity.ok(userTiliService.registerUserTili(userTili));
    }

    @PostMapping("/login")
    public ResponseEntity<UserTiliDTO> loginUserTili(@RequestBody UserTiliCreateDTO userTili){
        return userTiliService.loginUserTili(userTili).
                map(ResponseEntity.status(HttpStatus.OK)::body).
                orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

}
