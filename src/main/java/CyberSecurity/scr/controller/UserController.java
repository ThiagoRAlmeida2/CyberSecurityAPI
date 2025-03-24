package CyberSecurity.scr.controller;

import CyberSecurity.scr.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> registerUser(@RequestBody Map<String, String> request) {
        String nome = request.get("nome");
        String senha = request.get("senha");

        if (nome == null || senha == null) {
            return ResponseEntity.badRequest().body("Nome e senha são obrigatórios.");
        }

        return ResponseEntity.ok(userService.registerUser(nome, senha));
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody Map<String, String> request) {
        String nome = request.get("nome");
        String senha = request.get("senha");

        if (userService.loginUser(nome, senha)) {
            return ResponseEntity.ok("Login bem-sucedido!");
        } else {
            return ResponseEntity.status(401).body("Nome ou senha inválidos.");
        }
    }
}
