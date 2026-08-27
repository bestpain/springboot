package JwtAuthentication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestBody(

        @NotBlank
        @Email
        String email,

        @NotBlank
        String password

) {
}
