package JwtAuthentication.user;

import JwtAuthentication.dto.UserRegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping("/users/me")
    public ResponseEntity<UserRegisterResponse> getUser(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body(customUserDetailsService.getUserByID(user.getUuid()));
    }
}
