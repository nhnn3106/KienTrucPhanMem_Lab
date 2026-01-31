package fit.iuh.se.technicaldemo.controllers;

import fit.iuh.se.technicaldemo.models.User;
import fit.iuh.se.technicaldemo.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        authService.register(user);
        return "Đăng ký thành công!";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        if (authService.authenticate(user.getUsername(), user.getPassword())) {
            return "Đăng nhập thành công! (Mô phỏng: Trả về JWT tại đây)";
        }
        return "Sai tài khoản hoặc mật khẩu!";
    }
}
