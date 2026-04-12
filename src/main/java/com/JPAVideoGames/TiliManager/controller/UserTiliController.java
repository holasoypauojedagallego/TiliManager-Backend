package com.JPAVideoGames.TiliManager.controller;

import com.JPAVideoGames.TiliManager.dto.UserTiliCreateDTO;
import com.JPAVideoGames.TiliManager.dto.UserTiliDTO;
import com.JPAVideoGames.TiliManager.dto.UserTiliLoginDTO;
import com.JPAVideoGames.TiliManager.model.AuthResponse;
import com.JPAVideoGames.TiliManager.service.UserTiliService;
import jakarta.validation.Valid;
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

    @GetMapping("/name/{name}")
    public ResponseEntity<UserTiliDTO> getUserTiliByName(@PathVariable String name){
        return userTiliService.getByName(name).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/exists/name/{name}")
    public ResponseEntity<Boolean> existsUserTiliByName(@PathVariable String name){
        return ResponseEntity.ok(userTiliService.getByName(name).isPresent());
    }

    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> existsUserTiliByEmail(@PathVariable String email){
        return ResponseEntity.ok(userTiliService.getByEmail(email).isPresent());
    }

    @PostMapping("/register")
    public ResponseEntity<UserTiliDTO> postUserTili(@RequestBody @Valid UserTiliCreateDTO userTili) {
        return ResponseEntity.ok(userTiliService.registerUserTili(userTili)); // Cambiar a created
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUserTili(@RequestBody @Valid UserTiliLoginDTO userTili){
        return ResponseEntity.ok(userTiliService.loginUserTili(userTili));

    }

}
