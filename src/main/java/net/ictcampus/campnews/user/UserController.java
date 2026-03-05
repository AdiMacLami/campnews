package net.ictcampus.campnews.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import net.ictcampus.campnews.auth.jwt.JwtDTO;
import net.ictcampus.campnews.auth.jwt.JwtService;
import net.ictcampus.campnews.user.dto.UpdateUserDTO;
import net.ictcampus.campnews.user.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@Tag(name = "UserController", description = "Processes /users endpoint requests")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Operation(summary = "Get all Users", description = "Get all Users from the database", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully found users", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "404", description = "User not found"),
    })
    @PreAuthorize("@authz.canAccessUser(authentication, #id)")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @Operation(summary = "Get an existing User by ID", description = "Searches a user based on the Id", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully found User", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "404", description = "User not found"),
    })
    @PreAuthorize("@authz.canAccessUser(authentication, #id)")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @Operation(summary = "Update an existing User", description = "Updates user details based on provided information", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated user", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = JwtDTO.class))}),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "409", description = "Failed to update user")
    })
    @PreAuthorize("@authz.canAccessUser(authentication, #id)")
    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<JwtDTO> update(@PathVariable Integer id, @Valid @RequestBody UpdateUserDTO user) {
        User updatedUser = userService.updateUser(id, user);

        String token = jwtService.generateToken(updatedUser);

        JwtDTO jwtDTO = new JwtDTO()
                .setAccessToken("Bearer " + token)
                .setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(jwtDTO);
    }
    @Operation(summary = "Delete an existing User", description = "Deletes user details based on provided information", security = {@SecurityRequirement(name = "JWT-Token")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully deleted user", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "User not found"),
    })
    @PreAuthorize("@authz.canAccessUser(authentication, #id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }


}
