package JwtAuthentication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegisterRequest(
        @NotBlank
        String name,

        @Size(min = 8)
        String password,

        @Email
        String email
        ) {
}
