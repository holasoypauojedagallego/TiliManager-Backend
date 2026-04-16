package com.JPAVideoGames.TiliManager.service;

import com.JPAVideoGames.TiliManager.dto.UserTiliCreateDTO;
import com.JPAVideoGames.TiliManager.dto.UserTiliDTO;
import com.JPAVideoGames.TiliManager.dto.UserTiliLoginDTO;
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

        Team teamFromUserTili = new Team();
        int numeroaleatorio = (int) (Math.random() * 100000000);
        teamFromUserTili.setName("T_" + numeroaleatorio);

        UserTili savedUserTili = userTiliRepository.save(userTili);

        teamFromUserTili.setOwner(savedUserTili);
        teamRepository.save(teamFromUserTili);

        return userTiliMapper.toDto(savedUserTili);
    }

    public Optional<UserTiliDTO> loginUserTili(UserTiliLoginDTO userTili) {
        return userTiliRepository.findByEmail(userTili.getEmail()).filter
                (u -> passwordEncoder.matches((userTili.getPassword()), u.getPassword())).
                map(userTiliMapper::toDto);
    }
}
