package com.JPAVideoGames.TiliManager.service;

import com.JPAVideoGames.TiliManager.dto.UserTiliCreateDTO;
import com.JPAVideoGames.TiliManager.dto.UserTiliDTO;
import com.JPAVideoGames.TiliManager.mapper.UserTiliMapper;
import com.JPAVideoGames.TiliManager.model.UserTili;
import com.JPAVideoGames.TiliManager.repository.UserTiliRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserTiliService {

    private final UserTiliRepository userTiliRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserTiliMapper userTiliMapper;

    public UserTiliService(UserTiliRepository userTiliRepository, PasswordEncoder passwordEncoder, UserTiliMapper userTiliMapper) {
        this.userTiliRepository = userTiliRepository;
        this.passwordEncoder = passwordEncoder;
        this.userTiliMapper = userTiliMapper;
    }

    public List<UserTiliDTO> getAll(){
        return userTiliMapper.toDto(userTiliRepository.findAll());
    }

    public Optional<UserTiliDTO> getById(long id){
        return userTiliRepository.findById(id).map(userTiliMapper::toDto);
    }

    public Optional<UserTiliDTO> getByEmail(String email){
        return userTiliRepository.findByEmail(email).map(userTiliMapper::toDto);
    }

    public UserTiliDTO registerUserTili(UserTiliCreateDTO userTiliCreateDTO) {
        UserTili userTili = userTiliMapper.toCreateEntity(userTiliCreateDTO);
        userTili.setPassword(passwordEncoder.encode(userTili.getPassword()));

        return userTiliMapper.toDto(userTiliRepository.save(userTili));
    }

    public Optional<UserTiliDTO> loginUserTili(UserTiliCreateDTO userTili) {
        return userTiliRepository.findByEmail(userTili.getEmail()).filter
                (u -> passwordEncoder.matches((userTili.getPassword()), u.getPassword())).
                map(userTiliMapper::toDto);
    }
}
