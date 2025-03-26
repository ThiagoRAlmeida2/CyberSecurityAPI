package CyberSecurity.scr.controller;

import CyberSecurity.scr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public boolean login(@RequestParam("nome") String nome, @RequestParam("senha") String senha, HttpServletRequest request) {
        return userService.loginUser(nome, senha, request);
    }
}