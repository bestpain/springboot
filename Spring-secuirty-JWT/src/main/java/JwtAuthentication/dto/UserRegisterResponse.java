package JwtAuthentication.dto;

import java.util.UUID;

public record UserRegisterResponse(UUID id, String userName, String email) {
}
