package net.ictcampus.campnews.auth.jwt;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class JwtDTO {
    private String accessToken;
    private long expiresIn;
}
