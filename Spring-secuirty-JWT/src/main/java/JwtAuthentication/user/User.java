package JwtAuthentication.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // index on this

    @Column(updatable = false, unique = true, nullable = false)
    @Getter
    private UUID uuid;

    @Column(nullable = false)
    private String userName;

    @Getter
    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    @Setter
    private String password;

    @PrePersist
    public void persist() {
        uuid = UUID.randomUUID();
    }

    public User(String name, String email) {
        userName = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", userName='" + userName + '\'' +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
