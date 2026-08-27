package JwtAuthentication.auth;

import JwtAuthentication.dto.LoginRequestBody;
import JwtAuthentication.dto.TokenResponse;
import JwtAuthentication.dto.UserRegisterRequest;
import JwtAuthentication.dto.UserRegisterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/users/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequestBody requestBody){
       return  ResponseEntity.ok(new TokenResponse(authService.signIn(requestBody)));
    }


    @PostMapping("/users/register")
    public ResponseEntity<UserRegisterResponse> registerUser(@RequestBody @Valid UserRegisterRequest userRegisterRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerUser(userRegisterRequest));
    }

}
