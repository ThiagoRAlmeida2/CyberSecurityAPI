package CyberSecurity.scr.controller;

import CyberSecurity.scr.exeption.UserNotFoundException;
import CyberSecurity.scr.model.User;
import CyberSecurity.scr.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{nome}")
    public ResponseEntity<User> buscarUsuarioPorNome(@PathVariable String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            User user = userService.buscarUsuarioPorNome(nome);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> listarTodosUsuarios() {
        List<User> users = userService.listarTodosUsuarios();
        return ResponseEntity.ok(users);
    }
}