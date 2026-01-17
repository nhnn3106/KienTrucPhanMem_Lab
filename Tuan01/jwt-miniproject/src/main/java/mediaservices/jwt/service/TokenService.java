package mediaservices.jwt.service;

import mediaservices.jwt.entity.Token;

public interface TokenService {
    Token createToken(Token token);

    Token findByToken(String token);
}
