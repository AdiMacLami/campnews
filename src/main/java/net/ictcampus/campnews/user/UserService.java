package net.ictcampus.campnews.user;

import jakarta.persistence.EntityNotFoundException;
import net.ictcampus.campnews.user.dto.UpdateUserDTO;
import net.ictcampus.campnews.user.dto.UserDTO;
import net.ictcampus.campnews.user.dto.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> getUsers(){
        return userRepository.findAll().stream()
                .map(UserMapper::toDto)
                .toList();
    }

    public UserDTO getUser(Integer id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return UserMapper.toDto(user);
    }

    public User updateUser(Integer id, UpdateUserDTO dto){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        String encodedPassword = dto.getPassword() != null && !dto.getPassword().isBlank()
                ? passwordEncoder.encode(dto.getPassword())
                : user.getPassword();


        UserMapper.updateFromDto(user, dto, encodedPassword);
        return userRepository.save(user);
    }

    public void deleteUser(Integer id){
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }
}


