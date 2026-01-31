import java.util.Base64;

public class SimplePasswordService implements IPasswordService {

    @Override
    public String encode(String password) {
        // Mã hóa đơn giản bằng Base64 (trong thực tế nên dùng BCrypt hoặc tương tự)
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        String encoded = encode(rawPassword);
        return encoded.equals(encodedPassword);
    }
}

