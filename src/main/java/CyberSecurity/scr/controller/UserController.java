package CyberSecurity.scr.controller;

import CyberSecurity.scr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        return "register";
    }

    @PostMapping("/register-submit")
    @ResponseBody // Adicione ResponseBody
    public String registerUser(@RequestParam("nome") String nome, @RequestParam("senha") String senha) {
        userService.registerUser(nome, senha);
        return "Usuário cadastrado com sucesso!"; // Retorne uma mensagem de sucesso
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login";
    }

    @PostMapping("/login-submit")
    @ResponseBody // Adicione ResponseBody
    public String loginUser(@RequestParam("nome") String nome, @RequestParam("senha") String senha, HttpServletRequest request) {
        boolean loggedIn = userService.loginUser(nome, senha, request);
        if (loggedIn) {
            return "Login bem-sucedido!"; // Retorne uma mensagem de sucesso
        } else {
            return "Login falhou."; // Retorne uma mensagem de erro
        }
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "bem-vindo";
    }

    @PostMapping("/excluir-usuario")
    @ResponseBody
    public String excluirUsuario(@RequestParam("nome") String nome) {
        return userService.excluirUsuario(nome);
    }
}