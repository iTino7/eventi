package tinoborrelli.eventi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tinoborrelli.eventi.entities.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameOrEmail(String username, String email);
}
