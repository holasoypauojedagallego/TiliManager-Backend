package com.JPAVideoGames.TiliManager.service;

import com.JPAVideoGames.TiliManager.dto.UserTiliCreateDTO;
import com.JPAVideoGames.TiliManager.dto.UserTiliDTO;
import com.JPAVideoGames.TiliManager.dto.UserTiliLoginDTO;
import com.JPAVideoGames.TiliManager.model.AuthResponse;
import com.JPAVideoGames.TiliManager.util.UserTiliMapper;
import com.JPAVideoGames.TiliManager.model.UserTili;
import com.JPAVideoGames.TiliManager.repository.UserTiliRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserTiliService {

    private final UserTiliRepository userTiliRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserTiliMapper userTiliMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserTiliService(UserTiliRepository userTiliRepository, PasswordEncoder passwordEncoder,
                           UserTiliMapper userTiliMapper,@Lazy AuthenticationManager authenticationManager,
                           JwtService jwtService) {
        this.userTiliRepository = userTiliRepository;
        this.passwordEncoder = passwordEncoder;
        this.userTiliMapper = userTiliMapper;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public List<UserTiliDTO> getAll(){
        return userTiliMapper.toDto(userTiliRepository.findAll());
    }

    public Optional<UserTiliDTO> getById(long id){
        return userTiliRepository.findById(id).map(userTiliMapper::toDto);
    }

    public Optional<UserTiliDTO> getByName(String name){
        return userTiliRepository.findByName(name).map(userTiliMapper::toDto);
    }

    public Optional<UserTiliDTO> getByEmail(String email){
        return userTiliRepository.findByEmail(email).map(userTiliMapper::toDto);
    }

    public UserTiliDTO registerUserTili(UserTiliCreateDTO userTiliCreateDTO) {
        if (userTiliCreateDTO.getEmail().isEmpty() || userTiliCreateDTO.getPassword().isEmpty() || userTiliCreateDTO.getName().isEmpty()){
            return null;
        }
        UserTili userTili = userTiliMapper.toCreateEntity(userTiliCreateDTO);
        userTili.setPassword(passwordEncoder.encode(userTili.getPassword()));

        return userTiliMapper.toDto(userTiliRepository.save(userTili));
    }

    public AuthResponse loginUserTili(UserTiliLoginDTO userTili) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userTili.getEmail(),
                        userTili.getPassword()
                ));

        String token = jwtService.generateToken(authentication);
        String getUsername = getByEmail(userTili.getEmail()).get().getName();

        return new AuthResponse(token, "Bearer", userTili.getEmail(), getUsername);
    }
}