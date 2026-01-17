package mediaservices.jwt.service;

import mediaservices.jwt.authen.UserPrincipal;
import mediaservices.jwt.entity.User;

public interface UserService {
    User createUser(User user);
    UserPrincipal findByUsername(String username);
}
