import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryUserRepository implements IUserRepository {
    private final Map<String, User> users = new HashMap<>();

    @Override
    public void save(User user) {
        users.put(user.getUserName(), user);
    }

    @Override
    public Optional<User> findByUserName(String username) {
        return Optional.ofNullable(users.get(username));
    }

    @Override
    public boolean existsByUsername(String username) {
        return users.containsKey(username);
    }
}

