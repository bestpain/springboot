package JwtAuthentication.user;

import JwtAuthentication.dto.UserRegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found: " + email));
    }

    public UserRegisterResponse getUserByID(UUID id) {
        User user = userRepository.findByUuid(id).orElseThrow(
                () -> new RuntimeException("User not found: " + id));
        return new UserRegisterResponse(user.getUuid(), user.getUsername(), user.getEmail());
    }
}
