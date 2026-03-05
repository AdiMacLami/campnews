package net.ictcampus.campnews.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import net.ictcampus.campnews.auth.dto.AuthUserDTO;
import net.ictcampus.campnews.auth.jwt.JwtDTO;
import net.ictcampus.campnews.auth.jwt.JwtService;
import net.ictcampus.campnews.user.dto.CreateUserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "AuthenticationController", description = "Processes /auth endpoint requests")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    public AuthenticationController(AuthenticationService authenticationService, JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
    }
    @Operation(summary = "Register an new User", description = "Register user details based on provided information")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Successfully registrated user", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<Void> register(@Valid @RequestBody CreateUserDTO createUserDTO) {
        authenticationService.signup(createUserDTO);
        return ResponseEntity.status(201).build();
    }
    @Operation(summary = "Login an existing User", description = "Login user details based on provided information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful login", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = JwtDTO.class))}),
            @ApiResponse(responseCode = "404", description = "User not found"),
    })
    @PostMapping("/login")
    public ResponseEntity<JwtDTO> authenticate(@Valid @RequestBody AuthUserDTO authUserDto) {
        String token = jwtService.generateToken(authenticationService.authenticate(authUserDto));
        JwtDTO jwtDTO = new JwtDTO()
                .setAccessToken("Bearer " + token)
                .setExpiresIn(jwtService.getExpirationTime());
        return ResponseEntity.ok(jwtDTO);
    }

}
