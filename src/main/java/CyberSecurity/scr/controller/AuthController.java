package CyberSecurity.scr.controller;

import CyberSecurity.scr.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/encrypt")
    public String encryptPassword(@RequestBody String password) {
        return authService.encryptPassword(password);
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, @RequestParam String email) throws UnknownHostException {
        return authService.login(username, password, email);
    }
}
