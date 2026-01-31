import java.util.Optional;

public interface IUserRepository {
    void save(User user);
    Optional<User> findByUserName(String username);
    boolean existsByUsername(String username);
}
