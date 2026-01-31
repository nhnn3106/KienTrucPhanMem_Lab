import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Khởi tạo các dependencies
        IUserRepository userRepository = new InMemoryUserRepository();
        IPasswordService passwordService = new SimplePasswordService();
        AuthDomainService authService = new AuthDomainService(userRepository, passwordService);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("===========================================");
        System.out.println("   HỆ THỐNG ĐĂNG KÝ VÀ ĐĂNG NHẬP");
        System.out.println("===========================================");

        while (running) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Đăng ký (Register)");
            System.out.println("2. Đăng nhập (Login)");
            System.out.println("3. Thoát");
            System.out.print("Chọn chức năng (1-3): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleRegister(authService, scanner);
                    break;
                case "2":
                    handleLogin(authService, scanner);
                    break;
                case "3":
                    running = false;
                    System.out.println("Tạm biệt!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại!");
            }
        }

        scanner.close();
    }

    private static void handleRegister(AuthDomainService authService, Scanner scanner) {
        System.out.println("\n--- ĐĂNG KÝ ---");

        System.out.print("Nhập tên người dùng: ");
        String username = scanner.nextLine().trim();

        System.out.print("Nhập mật khẩu: ");
        String password = scanner.nextLine().trim();

        System.out.print("Nhập email: ");
        String email = scanner.nextLine().trim();

        try {
            authService.register(username, password, email);
            System.out.println("✓ Đăng ký thành công!");
            System.out.println("Tài khoản: " + username);
            System.out.println("Email: " + email);
        } catch (RuntimeException e) {
            System.out.println("✗ Đăng ký thất bại: " + e.getMessage());
        }
    }

    private static void handleLogin(AuthDomainService authService, Scanner scanner) {
        System.out.println("\n--- ĐĂNG NHẬP ---");

        System.out.print("Nhập tên người dùng: ");
        String username = scanner.nextLine().trim();

        System.out.print("Nhập mật khẩu: ");
        String password = scanner.nextLine().trim();

        try {
            User user = authService.login(username, password);
            System.out.println("✓ Đăng nhập thành công!");
            System.out.println("Chào mừng: " + user.getUserName());
            System.out.println("Email: " + user.getEmail());
        } catch (RuntimeException e) {
            System.out.println("✗ Đăng nhập thất bại: " + e.getMessage());
        }
    }
}

