package fit.iuh.se.technicaldemo.repositories;

import fit.iuh.se.technicaldemo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String user);

    Optional<User> findByUsername(String username);
}
