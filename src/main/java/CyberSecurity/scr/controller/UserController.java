package CyberSecurity.scr.controller;

import CyberSecurity.scr.model.User;
import CyberSecurity.scr.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        logger.info("Acessando página de registro.");
        return "register";
    }

    @PostMapping("/register-submit")
    public String registerUser(@RequestParam("nome") String nome, @RequestParam("senha") String senha, Model model) {
        logger.info("Registrando usuário: nome={}, senha={}", nome, senha);
        String resultado = userService.registerUser(nome, senha);
        logger.info("Resultado do registro: {}", resultado);
        model.addAttribute("mensagem", resultado); // Adiciona mensagem de resultado ao modelo
        return "register"; // Retorna para a página de registro com a mensagem
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        logger.info("Acessando página de login.");
        return "login";
    }

    @PostMapping("/login-submit")
    public String loginUser(@RequestParam("nome") String nome, @RequestParam("senha") String senha, HttpServletRequest request, Model model) {
        logger.info("Tentativa de login: nome={}, senha={}", nome, senha);
        boolean loggedIn = userService.loginUser(nome, senha, request);
        logger.info("Resultado do login: {}", loggedIn);
        if (loggedIn) {
            return "redirect:/welcome"; // Redireciona para a página de boas-vindas em caso de sucesso
        } else {
            model.addAttribute("mensagem", "Nome de usuário ou senha incorretos."); // Adiciona mensagem de erro ao modelo
            return "login"; // Retorna para a página de login com a mensagem de erro
        }
    }

    @GetMapping("/welcome")
    public String welcome() {
        logger.info("Acessando página de boas-vindas.");
        return "bem-vindo";
    }

    @DeleteMapping("/excluir-usuario/{nome}")
    @ResponseBody
    public String excluirUsuario(@PathVariable String nome) {
        logger.warn("Excluindo usuário: nome={}", nome);
        String resultado = userService.excluirUsuario(nome);
        logger.info("Resultado da exclusão: {}", resultado);
        return resultado;
    }

    @GetMapping("/users")
    @ResponseBody
    public List<User> listarUsuarios() {
        return userService.listarTodosUsuarios();
    }

    @GetMapping("/users/{nome}")
    @ResponseBody
    public User buscarUsuario(@PathVariable String nome) {
        return userService.buscarUsuarioPorNome(nome);
    }
}