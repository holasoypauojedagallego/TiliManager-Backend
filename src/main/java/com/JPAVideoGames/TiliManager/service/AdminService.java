package com.JPAVideoGames.TiliManager.service;

import com.JPAVideoGames.TiliManager.dto.usertilidto.UserTiliLoginDTO;
import com.JPAVideoGames.TiliManager.dto.usertilidto.UserTiliPassDTO;
import com.JPAVideoGames.TiliManager.exceptions.AdminException;
import com.JPAVideoGames.TiliManager.model.Admin;
import com.JPAVideoGames.TiliManager.model.UserTiliRole;
import com.JPAVideoGames.TiliManager.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    @Lazy
    private UserTiliService userTiliService;

    @Autowired
    @Lazy
    private AdminRepository adminRepository;

    public List<Admin> getAll(UserTiliLoginDTO userTili) {
        Optional<UserTiliPassDTO> user = userTiliService.loginUserTili(userTili);
        if (user.isEmpty() || user.get().getRole() != UserTiliRole.ADMIN) {
            Admin admin = new Admin();
            admin.setLog("El usuario: " + userTili.getEmail() + ", Ha intentado iniciar sesión como admin a las " + new Date());
            adminRepository.save(admin);
            throw new AdminException("Ese usuario no es Administrador.");
        }
        Admin admin = new Admin();
        admin.setUserTili(user.get().getId());
        admin.setLog("El usuario: " + user.get().getId() + ", " +  user.get().getEmail() + ", Ha iniciado sesión como admin a las " + new Date());
        adminRepository.save(admin);
        return adminRepository.findAll();
    }
}
