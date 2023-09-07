package team05a.secondhand.oauth.data.dto;

import lombok.Builder;
import lombok.Getter;
import team05a.secondhand.jwt.Jwt;

@Getter
public class JwtResponse {

    Jwt tokens;

    @Builder
    private JwtResponse(Jwt tokens) {
        this.tokens = tokens;
    }
}
