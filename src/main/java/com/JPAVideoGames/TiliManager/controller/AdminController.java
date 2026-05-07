package com.JPAVideoGames.TiliManager.controller;

import com.JPAVideoGames.TiliManager.dto.usertilidto.UserTiliLoginDTO;
import com.JPAVideoGames.TiliManager.model.Admin;
import com.JPAVideoGames.TiliManager.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    @Lazy
    private AdminService adminService;

    @PostMapping("/logs")
    public ResponseEntity<List<Admin>> getAllLogs(@RequestBody @Valid UserTiliLoginDTO userTiliLoginDTO) {
        return ResponseEntity.ok(adminService.getAll(userTiliLoginDTO));
    }
}
