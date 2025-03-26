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

    @PostMapping("/login")
    public String login(@RequestParam String nome, @RequestParam String senha, @RequestParam String email) throws UnknownHostException {
        return authService.login(nome, senha, email);
    }

    @PostMapping("/registrar")
    public String registrar(@RequestParam String nome, @RequestParam String senha) {
        return authService.registerUser(nome, senha);
    }
}