package net.ictcampus.campnews.user.dto;

import net.ictcampus.campnews.user.User;

public class UserMapper {

    public static UserDTO toDto(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername()
        );
    }

    public static void updateFromDto(User user, UpdateUserDTO dto, String encodedPassword) {
        user.setUsername(dto.getUsername());
        user.setPassword(encodedPassword);
    }
}
