package JwtAuthentication.auth;

import JwtAuthentication.dto.LoginRequestBody;
import JwtAuthentication.dto.UserRegisterRequest;
import JwtAuthentication.dto.UserRegisterResponse;
import JwtAuthentication.user.User;
import JwtAuthentication.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public UserRegisterResponse registerUser(UserRegisterRequest request) {
        User user = new User(request.name(), request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        User savedUser = userRepository.save(user);
        return new UserRegisterResponse(savedUser.getUuid(), savedUser.getUsername(), savedUser.getEmail());
    }

    public String signIn(LoginRequestBody requestBody) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(requestBody.email(), requestBody.password());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return jwtService.generateToken(authentication);
    }
}

