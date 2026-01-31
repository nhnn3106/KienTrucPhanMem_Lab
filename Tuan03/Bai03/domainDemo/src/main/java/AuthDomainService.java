public class AuthDomainService {
    private final IUserRepository userRepository;
    private final IPasswordService passwordService;

    public AuthDomainService(IUserRepository userRepository, IPasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }
    // Chức năng Đăng ký (Register)
    public void register(String username, String rawPassword, String email) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Tên người dùng đã tồn tại!");
        }

        // Domain gọi Interface để mã hóa, không cần biết thuật toán gì
        String encodedPassword = passwordService.encode(rawPassword);
        User newUser = new User(username, encodedPassword, email);

        userRepository.save(newUser);
    }

    // Chức năng Đăng nhập (Login)
    public User login(String username, String rawPassword) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại!"));

        if (!passwordService.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Mật khẩu không chính xác!");
        }

        return user;
    }

}
