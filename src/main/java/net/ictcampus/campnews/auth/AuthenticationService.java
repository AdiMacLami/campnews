package net.ictcampus.campnews.auth;

import jakarta.persistence.EntityExistsException;
import net.ictcampus.campnews.auth.dto.AuthUserDTO;
import net.ictcampus.campnews.user.User;
import net.ictcampus.campnews.user.UserRepository;
import net.ictcampus.campnews.user.dto.CreateUserDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public void signup(CreateUserDTO dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new EntityExistsException("Username already taken.");
        }

        User user = new User()
                .setUsername(dto.getUsername())
                .setPassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.save(user);
    }
    public User authenticate(AuthUserDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        return userRepository.findByUsername(dto.getUsername()).get();
    }

}
