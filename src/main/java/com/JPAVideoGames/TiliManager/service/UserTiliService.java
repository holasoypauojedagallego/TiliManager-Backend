package com.JPAVideoGames.TiliManager.service;

import com.JPAVideoGames.TiliManager.dto.UserTiliCreateDTO;
import com.JPAVideoGames.TiliManager.dto.UserTiliDTO;
import com.JPAVideoGames.TiliManager.dto.UserTiliLoginDTO;
import com.JPAVideoGames.TiliManager.dto.UserTiliPassDTO;
import com.JPAVideoGames.TiliManager.model.Team;
import com.JPAVideoGames.TiliManager.repository.TeamRepository;
import com.JPAVideoGames.TiliManager.util.UserTiliMapper;
import com.JPAVideoGames.TiliManager.model.UserTili;
import com.JPAVideoGames.TiliManager.repository.UserTiliRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserTiliService {

    private final UserTiliRepository userTiliRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserTiliMapper userTiliMapper;
    private final TeamRepository teamRepository;

    public UserTiliService(UserTiliRepository userTiliRepository, PasswordEncoder passwordEncoder,
                           UserTiliMapper userTiliMapper,  TeamRepository teamRepository) {
        this.userTiliRepository = userTiliRepository;
        this.passwordEncoder = passwordEncoder;
        this.userTiliMapper = userTiliMapper;
        this.teamRepository = teamRepository;
    }

    public List<UserTiliDTO> getAll(){
        return userTiliMapper.toDto(userTiliRepository.findAll());
    }

    public Optional<UserTiliDTO> getById(UUID id){
        return userTiliRepository.findById(id).map(userTiliMapper::toDto);
    }

    public Optional<UserTiliDTO> getByName(String name){
        return userTiliRepository.findByName(name).map(userTiliMapper::toDto);
    }

    public Optional<UserTiliDTO> getByEmail(String email){
        return userTiliRepository.findByEmail(email).map(userTiliMapper::toDto);
    }

    public UserTiliDTO registerUserTili(UserTiliCreateDTO userTiliCreateDTO) {
        if (userTiliCreateDTO.getEmail().isBlank() || userTiliCreateDTO.getEmail() == null ||
                userTiliCreateDTO.getPassword().isBlank() || userTiliCreateDTO.getPassword() == null ||
                userTiliCreateDTO.getName().isBlank() || userTiliCreateDTO.getName() == null){
            return null;
        }
        UserTili userTili = userTiliMapper.toCreateEntity(userTiliCreateDTO);
        userTili.setPassword(passwordEncoder.encode(userTili.getPassword()));

        UserTili savedUserTili = userTiliRepository.save(userTili);

        Team teamFromUserTili = new Team();
        int numeroaleatorio = (int) (Math.random() * 100000000);
        teamFromUserTili.setName("T_" + numeroaleatorio);
        teamFromUserTili.setMoney(125000000L);

        teamFromUserTili.setOwner(savedUserTili);
        teamRepository.save(teamFromUserTili);

        return userTiliMapper.toDto(savedUserTili);
    }

    public Optional<UserTiliPassDTO> loginUserTili(UserTiliLoginDTO userTili) {
        return userTiliRepository.findByEmail(userTili.getEmail()).filter
                (u -> passwordEncoder.matches((userTili.getPassword()), u.getPassword())).
                map(userTiliMapper::toPassDto);
    }
}
