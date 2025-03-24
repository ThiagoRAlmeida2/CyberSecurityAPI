package CyberSecurity.scr.service;

import CyberSecurity.scr.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String registerUser(String nome, String senha) {
        String senhaCriptografada = passwordEncoder.encode(senha);
        users.add(new User(nome, senhaCriptografada));
        return "Usuário cadastrado com sucesso!";
    }

    public boolean loginUser(String nome, String senha) {
        for (User user : users) {
            if (user.getNome().equals(nome) && passwordEncoder.matches(senha, user.getSenha())) {
                return true;
            }
        }
        return false;
    }
}

