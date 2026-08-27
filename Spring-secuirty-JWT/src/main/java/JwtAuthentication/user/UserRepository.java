package JwtAuthentication.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByUuid(UUID id);

    public Optional<UserDetails> findByEmail(String email);
}
