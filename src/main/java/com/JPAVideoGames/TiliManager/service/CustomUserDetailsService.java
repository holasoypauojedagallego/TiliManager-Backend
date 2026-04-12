package com.JPAVideoGames.TiliManager.service;

import com.JPAVideoGames.TiliManager.model.UserTili;
import com.JPAVideoGames.TiliManager.repository.UserTiliRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserTiliRepository userTiliRepository;

    public CustomUserDetailsService(UserTiliRepository userTiliRepository) {
        this.userTiliRepository = userTiliRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserTili user = userTiliRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword()) // Ya debe estar encriptada en la BD
                .build();
    }
}